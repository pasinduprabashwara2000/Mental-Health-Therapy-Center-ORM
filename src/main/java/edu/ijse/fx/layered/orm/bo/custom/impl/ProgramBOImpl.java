package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.ProgramBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.ProgramDAO;
import edu.ijse.fx.layered.orm.dto.ProgramDTO;
import edu.ijse.fx.layered.orm.entity.ProgramEntity;
import java.util.ArrayList;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public boolean save(ProgramDTO programDTO) throws Exception {

        ProgramEntity programEntity = new ProgramEntity(
              programDTO.getId(),
              programDTO.getName(),
              programDTO.getDuration(),
              programDTO.getCost()
        );

        return programDAO.save(programEntity);

    }

    @Override
    public boolean update(ProgramDTO programDTO) throws Exception {

        ProgramEntity programEntity = new ProgramEntity(
                programDTO.getId(),
                programDTO.getName(),
                programDTO.getDuration(),
                programDTO.getCost()
        );

        return programDAO.update(programEntity);

    }

    @Override
    public boolean delete(String id) throws Exception {
        return programDAO.delete(id);
    }

    @Override
    public ProgramDTO search(String id) throws Exception {

        ProgramEntity programEntity = programDAO.search(id);

        if (programEntity != null) {
            return new ProgramDTO(
              programEntity.getId(),
              programEntity.getName(),
              programEntity.getDuration(),
              programEntity.getCost()
            );
        }

        return null;

    }

    @Override
    public ArrayList<ProgramDTO> getAll() throws Exception {

        ArrayList<ProgramEntity> programEntities = programDAO.getAll();
        ArrayList<ProgramDTO> programDTOS = new ArrayList<>();

        for (ProgramEntity programEntity : programEntities){
            programDTOS.add(new ProgramDTO(
               programEntity.getId(),
               programEntity.getName(),
               programEntity.getDuration(),
               programEntity.getCost()
            ));
        }

        return programDTOS;
    }
}
