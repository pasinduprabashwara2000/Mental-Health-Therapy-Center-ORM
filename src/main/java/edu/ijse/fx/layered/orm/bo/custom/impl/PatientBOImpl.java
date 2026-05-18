package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.PatientBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.PatientDAO;
import edu.ijse.fx.layered.orm.dto.PatientDTO;
import edu.ijse.fx.layered.orm.entity.PatientEntity;
import java.util.ArrayList;

public class PatientBOImpl implements PatientBO {

    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public boolean save(PatientDTO patientDTO) throws Exception {

        PatientEntity patientEntity = new PatientEntity(
            patientDTO.getPatientId(),
            patientDTO.getName(),
            patientDTO.getAge(),
            patientDTO.getGender(),
            patientDTO.getContactNumber(),
            patientDTO.getAddress(),
            patientDTO.getDisease(),
            patientDTO.getSessions()
        );

        return patientDAO.save(patientEntity);

    }

    @Override
    public boolean update(PatientDTO patientDTO) throws Exception {

        PatientEntity patientEntity = new PatientEntity(
                patientDTO.getPatientId(),
                patientDTO.getName(),
                patientDTO.getAge(),
                patientDTO.getGender(),
                patientDTO.getContactNumber(),
                patientDTO.getAddress(),
                patientDTO.getDisease(),
                null
        );

        return patientDAO.update(patientEntity);

    }

    @Override
    public boolean delete(String id) throws Exception {
        return patientDAO.delete(id);
    }

    @Override
    public PatientDTO search(String id) throws Exception {

        PatientEntity patientEntity = patientDAO.search(id);

        if (patientEntity != null) {
            return new PatientDTO(
                    patientEntity.getPatientId(),
                    patientEntity.getName(),
                    patientEntity.getAge(),
                    patientEntity.getGender(),
                    patientEntity.getContactNumber(),
                    patientEntity.getAddress(),
                    patientEntity.getDisease(),
                    null
            );
        }
        return null;
    }

    @Override
    public ArrayList<PatientDTO> getAll() throws Exception {

        ArrayList<PatientEntity> patientEntities = patientDAO.getAll();
        ArrayList<PatientDTO> patientDTOS = new ArrayList<>();

        for (PatientEntity patientEntity : patientEntities){
            patientDTOS.add(new PatientDTO(
                    patientEntity.getPatientId(),
                    patientEntity.getName(),
                    patientEntity.getAge(),
                    patientEntity.getGender(),
                    patientEntity.getContactNumber(),
                    patientEntity.getAddress(),
                    patientEntity.getDisease(),
                    null
            ));
        }

        return patientDTOS;

    }
}
