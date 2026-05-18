package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.PaymentBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.PaymentDAO;
import edu.ijse.fx.layered.orm.dao.custom.SessionDAO;
import edu.ijse.fx.layered.orm.dto.PaymentDTO;
import edu.ijse.fx.layered.orm.entity.PaymentEntity;
import edu.ijse.fx.layered.orm.entity.SessionEntity;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
    private final SessionDAO sessionDAO = (SessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SESSION);

    @Override
    public boolean save(PaymentDTO paymentDTO) throws Exception {

        SessionEntity sessionId = sessionDAO.search(paymentDTO.getSessionId());

        PaymentEntity paymentEntity = new PaymentEntity(
                paymentDTO.getPaymentId(),
                sessionId,
                paymentDTO.getAmount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus()
        );

        return paymentDAO.save(paymentEntity);

    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws Exception {

        SessionEntity sessionId = sessionDAO.search(paymentDTO.getSessionId());

        PaymentEntity paymentEntity = new PaymentEntity(
                paymentDTO.getPaymentId(),
                sessionId,
                paymentDTO.getAmount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus()
        );

        return paymentDAO.update(paymentEntity);

    }

    @Override
    public boolean delete(String id) throws Exception {
        return paymentDAO.delete(id);
    }

    @Override
    public PaymentDTO search(String id) throws Exception {

        PaymentEntity paymentEntity = paymentDAO.search(id);

        if(paymentEntity != null){
            return new PaymentDTO(
                    paymentEntity.getPaymentId(),
                    paymentEntity.getSessionId() != null ? paymentEntity.getSessionId().getSessionId() : null,
                    paymentEntity.getAmount(),
                    paymentEntity.getPaymentMethod(),
                    paymentEntity.getPaymentDate(),
                    paymentEntity.getStatus()
            );
        }

        return null;
    }

    @Override
    public ArrayList<PaymentDTO> getAll() throws Exception {

        ArrayList<PaymentEntity> paymentEntities = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        for (PaymentEntity paymentEntity : paymentEntities){
            paymentDTOS.add(new PaymentDTO(
               paymentEntity.getPaymentId(),
               paymentEntity.getSessionId() != null ? paymentEntity.getSessionId().getSessionId() : null,
               paymentEntity.getAmount(),
               paymentEntity.getPaymentMethod(),
               paymentEntity.getPaymentDate(),
               paymentEntity.getStatus()
            ));
        }

        return paymentDTOS;
    }
}
