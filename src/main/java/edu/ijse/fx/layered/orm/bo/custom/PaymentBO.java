package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.PaymentDTO;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {

    boolean save(PaymentDTO paymentDTO) throws Exception;
    boolean update(PaymentDTO paymentDTO) throws Exception;
    boolean delete(String id) throws Exception;
    PaymentDTO search(String id) throws Exception;
    ArrayList<PaymentDTO> getAll() throws Exception;

}
