package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.TherapistBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.TherapistDAO;
import edu.ijse.fx.layered.orm.dto.TherapistDTO;
import edu.ijse.fx.layered.orm.entity.TherapistEntity;
import java.util.ArrayList;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);

    @Override
    public boolean save(TherapistDTO therapistDTO) throws Exception {

        TherapistEntity therapistEntity = new TherapistEntity(
                therapistDTO.getId(),
                therapistDTO.getTherapistName(),
                therapistDTO.getProgramName(),
                therapistDTO.getSpecialization(),
                therapistDTO.getContactNo()
        );
        return therapistDAO.save(therapistEntity);
    }

    @Override
    public boolean update(TherapistDTO therapistDTO) throws Exception {

        TherapistEntity therapistEntity =new TherapistEntity(
                therapistDTO.getId(),
                therapistDTO.getTherapistName(),
                therapistDTO.getProgramName(),
                therapistDTO.getSpecialization(),
                therapistDTO.getContactNo()
        );
        return therapistDAO.update(therapistEntity);
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return therapistDAO.delete(id);
    }

    @Override
    public TherapistDTO search(Integer id) throws Exception {

        TherapistEntity therapistEntity = therapistDAO.search(id);

        if(therapistEntity != null){
            return new TherapistDTO(
              therapistEntity.getId(),
              therapistEntity.getTherapistName(),
              therapistEntity.getProgramName(),
              therapistEntity.getSpecialization(),
              therapistEntity.getContactNo()
            );
        }
        return null;
    }

    @Override
    public ArrayList<TherapistDTO> getAll() throws Exception {

        ArrayList<TherapistEntity> therapistEntities = therapistDAO.getAll();
        ArrayList<TherapistDTO> therapistDTOS = new ArrayList<>();

        for (TherapistEntity therapistEntity : therapistEntities){
            therapistDTOS.add(new TherapistDTO(
               therapistEntity.getId(),
               therapistEntity.getTherapistName(),
               therapistEntity.getProgramName(),
               therapistEntity.getSpecialization(),
               therapistEntity.getContactNo()
            ));
        }

        return therapistDTOS;

    }

}
