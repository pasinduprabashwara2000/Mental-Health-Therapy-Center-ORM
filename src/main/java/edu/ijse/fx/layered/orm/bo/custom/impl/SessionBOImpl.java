package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.SessionBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.PatientDAO;
import edu.ijse.fx.layered.orm.dao.custom.SessionDAO;
import edu.ijse.fx.layered.orm.dao.custom.TherapistDAO;
import edu.ijse.fx.layered.orm.dto.SessionDTO;
import edu.ijse.fx.layered.orm.entity.PatientEntity;
import edu.ijse.fx.layered.orm.entity.SessionEntity;
import edu.ijse.fx.layered.orm.entity.TherapistEntity;
import java.util.ArrayList;

public class SessionBOImpl implements SessionBO {

    SessionDAO sessionDAO = (SessionDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SESSION);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public boolean save(SessionDTO sessionDTO) throws Exception {

        TherapistEntity therapistId = therapistDAO.search(sessionDTO.getTherapistId());
        PatientEntity patientId = patientDAO.search(sessionDTO.getPatientId());

        SessionEntity sessionEntity = new SessionEntity(
                sessionDTO.getSessionId(),
                therapistId,
                patientId,
                sessionDTO.getDate()
        );

        return sessionDAO.save(sessionEntity);

    }

    @Override
    public boolean update(SessionDTO sessionDTO) throws Exception {

        TherapistEntity therapistId = therapistDAO.search(sessionDTO.getTherapistId());
        PatientEntity patientId = patientDAO.search(sessionDTO.getPatientId());

        SessionEntity sessionEntity = new SessionEntity(
            sessionDTO.getSessionId(),
            therapistId,
            patientId,
            sessionDTO.getDate()
        );

        return sessionDAO.update(sessionEntity);

    }

    @Override
    public boolean delete(String id) throws Exception {
        return sessionDAO.delete(id);
    }

    @Override
    public SessionDTO search(String id) throws Exception {

        SessionEntity sessionEntity = sessionDAO.search(id);

        if(sessionEntity != null){
            return new SessionDTO(
                    sessionEntity.getSessionId(),
                    sessionEntity.getTherapistId() != null ? sessionEntity.getTherapistId().getTherapistId() : null,
                    sessionEntity.getPatientId() !=null ? sessionEntity.getPatientId().getPatientId() : null,
                    sessionEntity.getDate()
            );
        }

        return null;

    }

    @Override
    public ArrayList<SessionDTO> getAll() throws Exception {

        ArrayList<SessionEntity> sessionEntities = sessionDAO.getAll();
        ArrayList<SessionDTO> sessionDTOS = new ArrayList<>();

        for (SessionEntity sessionEntity : sessionEntities){
            sessionDTOS.add(new SessionDTO(
                sessionEntity.getSessionId(),
                sessionEntity.getTherapistId() != null ? sessionEntity.getTherapistId().getTherapistId() : null,
                sessionEntity.getPatientId() != null ? sessionEntity.getPatientId().getPatientId() : null,
                sessionEntity.getDate()
            ));
        }

        return sessionDTOS;

    }
}
