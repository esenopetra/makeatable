package com.maverix.makeatable.services;

import com.maverix.makeatable.dto.Orders.*;
import com.maverix.makeatable.exceptions.OrderNotFoundException;
import com.maverix.makeatable.models.Orders;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.OrdersRepository;
import com.maverix.makeatable.repositories.RestaurantRepository;
import com.maverix.makeatable.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public OrdersService(OrdersRepository ordersRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.ordersRepository = ordersRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public List<OrdersGetDto> getAllOrders() {
        List<Orders> ordersList = ordersRepository.findAll();
        return ordersList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<OrderDetailsDTO> getOrderDetailsByRestaurantId(Long restaurantId) {
        List<Orders> orders = ordersRepository.findByRestaurantId(restaurantId);
        return orders.stream().map(this::mapOrderToOrderDetailsDTO).collect(Collectors.toList());
    }

    private OrderDetailsDTO mapOrderToOrderDetailsDTO(Orders order) {
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setRoomType(order.getTypeRoom());
        orderDetailsDTO.setNumberOfSeats(Math.toIntExact(order.getSeatNum()));
        orderDetailsDTO.setUserName(order.getCreatedByUser().getFullName());
        return orderDetailsDTO;
    }
    public LastOrderDto getLastOrderForUser(Long userId){
        Optional<Orders> lastOrderOptional = ordersRepository.findTopByCreatedByUserIdOrderByFromDateTimeDesc(userId);
        if (lastOrderOptional.isPresent()) {
            Orders lastOrder = lastOrderOptional.get();
            String location = "";
            if (lastOrder.getRestaurant() != null) {
                location = lastOrder.getRestaurant().getLocation();
            }

            LastOrderDto lastOrderDto = new LastOrderDto();
            lastOrderDto.setId(lastOrder.getId());
            lastOrderDto.setLocation(location);
            lastOrderDto.setDateTime(lastOrder.getFromDateTime());
            lastOrderDto.setRoomType(lastOrder.getTypeRoom());

            return new OrderResponseDTO(lastOrderDto).getLastOrder();
        } else {

            return null;
        }
    }
    public OrdersGetDto getOrderById(Long id) {
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        return optionalOrders.map(this::convertToDto).orElse(null);
    }

    public OrdersGetDto createOrder(OrdersPostDto ordersPostDto) {
        Orders orders = convertToEntity(ordersPostDto);
        Orders savedOrder = ordersRepository.save(orders);
        return convertToDto(savedOrder);
    }
    public OrdersGetDto createOrder(OrdersPostDto orderRequest, Long userId) {

        Restaurant restaurant = restaurantRepository.getById(orderRequest.getRestaurantId());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        Orders order = new Orders();
        order.setCreatedAt(LocalDateTime.now());
        order.setSeatNum(orderRequest.getSeatNum());
        order.setTypeRoom(orderRequest.getRoomType());
        order.setRestaurant(restaurant);
        order.setCreatedByUser(user);
        order.setFromDateTime(orderRequest.getDateTime());
        order.setToDateTime(orderRequest.getDateTime().plusMinutes(15));
        ordersRepository.save(order);
        return convertToDto(order);
    }

    public OrdersGetDto updateOrder(Long id, OrdersPutDto ordersPutDto) {
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        if (optionalOrders.isPresent()) {
            Orders existingOrder = optionalOrders.get();
            BeanUtils.copyProperties(ordersPutDto, existingOrder);
            existingOrder.setUpdatedAt(LocalDateTime.now());
            Orders updatedOrder = ordersRepository.save(existingOrder);
            return convertToDto(updatedOrder);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }

    private OrdersGetDto convertToDto(Orders orders) {
        OrdersGetDto ordersDto = new OrdersGetDto();
        BeanUtils.copyProperties(orders, ordersDto);
        if (orders.getCreatedByUser() != null) {
            ordersDto.setUserId(orders.getCreatedByUser().getId());
        }
        if (orders.getRestaurant() != null) {
            ordersDto.setRestaurantId(orders.getRestaurant().getId());
        }
        return ordersDto;
    }

    private Orders convertToEntity(OrdersPostDto ordersPostDto) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersPostDto, orders);
        return orders;
    }

    public boolean rescheduleOrder(Long orderId, OrderRescheduleDto rescheduleDto) {
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            LocalDateTime rescheduledDateTime = rescheduleDto.getRescheduledDateTime();
            if (rescheduledDateTime != null) {
                order.setFromDateTime(rescheduledDateTime);
                order.setToDateTime(rescheduledDateTime.plusMinutes(15));
                ordersRepository.save(order);
                System.out.println("Order with ID " + orderId + " rescheduled to: " + rescheduledDateTime);
                return true;
            } else {
                throw new IllegalArgumentException("Rescheduled date and time cannot be null.");
            }
        } else {
            throw new OrderNotFoundException("Order not found for ID: " + orderId);
        }
    }

    public List<OrdersGetDto> getOrdersForCurrentUser(Long userId) {
        List<Orders> userOrders = ordersRepository.findByCreatedByUserId(userId);

        if (userOrders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for the user with ID: " + userId);
        }
        return userOrders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
