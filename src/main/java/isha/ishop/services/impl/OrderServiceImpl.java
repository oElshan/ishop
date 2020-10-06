package isha.ishop.services.impl;

import isha.ishop.entity.ClientOrder;
import isha.ishop.entity.OrderItem;
import isha.ishop.entity.Status;
import isha.ishop.repository.OrderRepo;
import isha.ishop.repository.StatusRepo;
import isha.ishop.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    StatusRepo statusRepo;



    @Override
    public List<ClientOrder>  getAllNewOrders() {
       return orderRepo.findAllByStatusName("new");
    }

    @Override
    public long getCountNewOrders(String status) {
        return orderRepo.countAllByStatusName("new");
    }

    @Override
    public List<ClientOrder> getTodayOrder() {
        return orderRepo.findAllClientOrderToday();
    }

    @Override
    public List<ClientOrder> findOrderByName(String name) {

        List<ClientOrder> clientOrders = orderRepo.searchOrderByNameLike(name);
        return clientOrders;
    }

    @Override
    public Page<ClientOrder> getOrdersLimit(int page, int limit) {

       return   orderRepo.findAll(PageRequest.of(page-1,limit, Sort.Direction.DESC,"created"));
    }

    @Override
    public Page<ClientOrder> getOrdersLimit(int page, int limit, String status) {

//      return orderRepo.findAllByStatusSelect(status, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "created"));
        return orderRepo.findAllByStatusName(status, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "created"));
    }

    @Override
    public ClientOrder findOrderByPhone(String phone) {

        return orderRepo.findAllByClientPhone(phone);
    }


    @Override
    public List<Status>  getAllStatusOrders() {
        return statusRepo.findAll();
    }

    @Override
    public ClientOrder findClientOrderById(long id) {
        return orderRepo.findById(id);
    }


    @Transactional
    @Override
    public ClientOrder updateClientOrderItem(long orderId, long productId, int count) {
        ClientOrder clientOrder = orderRepo.findById(orderId);
        List<OrderItem> orderItems = clientOrder.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            int index = orderItems.indexOf(orderItem);
            if (orderItem.getId() == productId) {
                orderItem.setCount(count);
                orderItems.set(index, orderItem);
            }
        }
        clientOrder.setOrderItems(orderItems);
        return orderRepo.save(clientOrder);
    }
}
