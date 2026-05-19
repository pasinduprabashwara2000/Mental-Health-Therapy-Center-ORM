package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.AdminReportDAO;
import edu.ijse.fx.layered.orm.dto.AdminReportDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminReportDAOImpl implements AdminReportDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public ArrayList<AdminReportDTO> getTherapistPerformance(LocalDate from, LocalDate to, String therapistId) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            StringBuilder hql = new StringBuilder(
                    "SELECT t.therapistId, t.therapistName, " +
                            "COUNT(s.sessionId), " +
                            "SUM(CASE WHEN s.status = 'Completed' THEN 1 ELSE 0 END), " +
                            "SUM(CASE WHEN s.status = 'Cancelled' THEN 1 ELSE 0 END) " +
                            "FROM SessionEntity s " +
                            "JOIN s.therapist t " +
                            "WHERE 1=1 "
            );
            if (from != null)       hql.append("AND s.date >= :fromDate ");
            if (to != null)         hql.append("AND s.date <= :toDate ");
            if (therapistId != null && !therapistId.isEmpty() && !therapistId.equalsIgnoreCase("All"))
                hql.append("AND t.therapistId = :tid ");
            hql.append("GROUP BY t.therapistId, t.therapistName");

            var query = session.createQuery(hql.toString(), Object[].class);
            if (from != null)       query.setParameter("fromDate", from);
            if (to != null)         query.setParameter("toDate", to);
            if (therapistId != null && !therapistId.isEmpty() && !therapistId.equalsIgnoreCase("All"))
                query.setParameter("tid", therapistId);

            List<Object[]> rows = query.list();
            transaction.commit();

            ArrayList<AdminReportDTO> result = new ArrayList<>();
            for (Object[] row : rows) {
                String tid     = (String) row[0];
                String tname   = (String) row[1];
                long total     = row[2] != null ? (Long) row[2] : 0L;
                long completed = row[3] != null ? (Long) row[3] : 0L;
                long cancelled = row[4] != null ? (Long) row[4] : 0L;
                double perf    = total > 0 ? (completed * 100.0 / total) : 0.0;
                result.add(new AdminReportDTO(tid, tname, (int) total, (int) completed, (int) cancelled,
                        Math.round(perf * 10.0) / 10.0));
            }
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Map<String, Long> getSessionStatusStats(LocalDate from, LocalDate to) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            StringBuilder hql = new StringBuilder(
                    "SELECT s.status, COUNT(s.sessionId) FROM SessionEntity s WHERE 1=1 ");
            if (from != null) hql.append("AND s.date >= :fromDate ");
            if (to != null)   hql.append("AND s.date <= :toDate ");
            hql.append("GROUP BY s.status");

            var query = session.createQuery(hql.toString(), Object[].class);
            if (from != null) query.setParameter("fromDate", from);
            if (to != null)   query.setParameter("toDate", to);

            List<Object[]> rows = query.list();
            transaction.commit();

            Map<String, Long> map = new LinkedHashMap<>();
            for (Object[] row : rows) {
                map.put((String) row[0], (Long) row[1]);
            }
            return map;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<String> getAllTherapistIds() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            List<String> ids = session.createQuery(
                    "SELECT t.therapistId FROM TherapistEntity t", String.class).list();
            return new ArrayList<>(ids);
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<String> getAllSessionStatuses() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            List<String> statuses = session.createQuery(
                    "SELECT DISTINCT s.status FROM SessionEntity s", String.class).list();
            return new ArrayList<>(statuses);
        } finally {
            session.close();
        }
    }
}