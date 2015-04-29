package com.zemiak.online.service.notifications;

import com.zemiak.online.model.OutageDTO;
import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.model.ProtectedSystemDTO;
import com.zemiak.online.service.OutageService;
import com.zemiak.online.service.ProtectedSystemService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class DailyNotifier {
    private static final Logger LOG = Logger.getLogger(DailyNotifier.class.getName());
    private static final String LAST_SEEN = "Last seen: ";

    @Resource(name = "java:/online/mail/default")
    private Session mailSession;

    @Inject String mailTo;
    @Inject String mailFrom;
    @Inject String mailSubjectDailyOk;
    @Inject String mailSubjectDailyFailures;

    @Inject ProtectedSystemService systems;
    @Inject OutageService outages;

    @Schedule(minute = "0", hour="3", second="30")
    public void check() {
        boolean failure = false;
        String text = "";

        for (ProtectedSystem system: systems.all()) {
            if (system.isDisabled()) {
                continue;
            }

            if (system.isOutage() || wasOutageLastDay(system)) {
                failure = true;
            }

            text += getSystemReport(system);
        }

        try {
            send(failure ? mailSubjectDailyFailures : mailSubjectDailyOk, text);
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Cannot send mail: {0}", ex);
        }
    }

    private void send(String mailSubject, String text) throws MessagingException {
        final Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setSubject(mailSubject);
        message.setText(text);

        Transport.send(message);

        LOG.log(Level.INFO, "Sent {0} to {1}", new Object[]{mailSubject, mailTo});
    }

    private String getSystemReport(ProtectedSystem system) {
        ProtectedSystemDTO dto = new ProtectedSystemDTO(system);
        String text = "";

        if (system.isOutage()) {
            text += system.getName() + ": RUNNING OUTAGE\n";
            text += LAST_SEEN + dto.getLastSeen() + "\n\n";
        } else if (wasOutageLastDay(system)) {
            text += system.getName() + ": Outage in the last 24 hours\n";
            text += LAST_SEEN + dto.getLastSeen() + "\n";
            text += dumpLastDayOutages(system);
        } else {
            text += system.getName() + ": OK\n";
            text += LAST_SEEN + dto.getLastSeen() + "\n\n";
        }

        return text;
    }

    private boolean wasOutageLastDay(ProtectedSystem system) {
        return !outages.findLastDayByEnd(system.getId()).isEmpty();
    }

    private String dumpLastDayOutages(ProtectedSystem system) {
        final StringBuilder sb = new StringBuilder("Outages:\n");

        outages.findLastDayByEnd(system.getId()).stream()
                .map(OutageDTO::new)
                .forEach(o -> sb.append("Start: ").append(o.getStart())
                            .append(", end: ").append(o.getEnd())
                            .append(", duration: ").append(o.getDuration())
                            .append("\n")
                );

        return sb.toString();
    }
}
