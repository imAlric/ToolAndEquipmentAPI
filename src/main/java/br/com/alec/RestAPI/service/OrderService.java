package br.com.alec.RestAPI.service;

import br.com.alec.RestAPI.exception.Order.*;
import br.com.alec.RestAPI.model.Log;
import br.com.alec.RestAPI.model.Order;
import br.com.alec.RestAPI.model.Status;
import br.com.alec.RestAPI.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class OrderService{
    @Autowired
    ToolRepository ToolRepo;
    @Autowired
    OrderRepository OrderRepo;
    @Autowired
    CustomerRepository CustomerRepo;
    @Autowired
    StaffRepository StaffRepo;
    @Autowired
    LogRepository LogRepo;

    public Order save(Order newOrder){
        try{
            if(ToolRepo.findById(newOrder.getTool().getId()).isEmpty()) ToolRepo.save(newOrder.getTool());

            if((CustomerRepo.findById(newOrder.getCustomer().getId()).isEmpty())
                    && (CustomerRepo.findByCPF(newOrder.getCustomer().getCpf()).isEmpty())) CustomerRepo.save(newOrder.getCustomer());

            if((StaffRepo.findById(newOrder.getStaff().getId()).isEmpty())
                    && (StaffRepo.findByCPF(newOrder.getStaff().getCpf()).isEmpty())) StaffRepo.save(newOrder.getStaff());

            return OrderRepo.save(newOrder);
        }
        catch(Exception e){
            throw new OrderNotCreatedExcep();
        }
    }

    public Order interrupt(Order order, Log newLog){
        try{
            //Log.
            newLog.setAction("Interrupt");
            newLog.setTarget("Order");
            newLog.setTarget_id(order.getId());
            LogRepo.save(newLog);

            //Executa a interrupção.
            order.setLog(newLog);
            order.setStatus(Status.Interrupted);
            return OrderRepo.save(order);
        } catch(Exception e){
            throw new OrderNotUpdatedExcep(order);
        }
    }

    public Order resume(Order order, Log newLog){
        try{
            //Log.
            newLog.setAction("Resume");
            newLog.setTarget("Order");
            newLog.setTarget_id(order.getId());
            LogRepo.save(newLog);

            //Retomar ordem de serviço.
            order.setLog(newLog);
            order.setStatus(Status.Pending);
            return OrderRepo.save(order);
        } catch (Exception e){
            throw new OrderNotUpdatedExcep(order);
        }
    }

    public Order close(Order order, Log newLog){
        try{
            //Log.
            newLog.setAction("Close");
            newLog.setTarget("Order");
            newLog.setTarget_id(order.getId());
            LogRepo.save(newLog);

            //Executa a baixa.
            order.setLog(newLog);
            order.setClose_date(new Date());
            order.setStatus(Status.Closed);
            return OrderRepo.save(order);
        } catch (Exception e){
            throw new OrderNotUpdatedExcep(order);
        }
    }

    public Order delete(Order order){
        try{
            //Log.
            Log log = new Log();
            log.setAction("Delete");
            log.setTarget("Order");
            log.setTarget_id(order.getId());
            LogRepo.save(log);

            //Executa o delete.
            order.setStatus(Status.Deleted);
            return OrderRepo.save(order);
        } catch (Exception e){
            throw new OrderNotUpdatedExcep(order);
        }
    }
}
