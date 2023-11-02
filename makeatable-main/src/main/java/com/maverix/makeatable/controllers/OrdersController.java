package com.maverix.makeatable.controllers;

import com.maverix.makeatable.config.Security.JwtService;
import com.maverix.makeatable.dto.Orders.*;
import com.maverix.makeatable.services.OrdersService;
import com.maverix.makeatable.services.RestaurantService;
import com.maverix.makeatable.util.JwtUtils;
import com.maverix.makeatable.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {
    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
    private final RestaurantService restaurantService;
    private final OrdersService ordersService;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;

    private final JwtService jwtService;

    public OrdersController(RestaurantService restaurantService, OrdersService ordersService, HttpServletRequest request, JwtUtils jwtUtils, JwtService jwtService) {
        this.restaurantService = restaurantService;
        this.ordersService = ordersService;
        this.request = request;
        this.jwtUtils = jwtUtils;
        this.jwtService = jwtService;
    }
    @GetMapping("restaurant/{restaurantId}")
    public Response<List<OrderDetailsDTO>> getOrdersForRestaurant(@PathVariable Long restaurantId) {
        Long jwtUserId = Long.valueOf(jwtService.extractId(jwtUtils.getJwtFromRequest(request)));
        if(restaurantService.isManagerOfRestaurantbyId(restaurantId,jwtUserId)) {
            List<OrderDetailsDTO> orderDetailsDTOList = ordersService.getOrderDetailsByRestaurantId(restaurantId);
            return Response.<List<OrderDetailsDTO>>builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Order details fetched successfully")
                    .data(orderDetailsDTOList)
                    .timeStamp(LocalDateTime.now())
                    .build();
        }
        else {
            return Response.<List<OrderDetailsDTO>>builder()
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .status(HttpStatus.FORBIDDEN)
                    .message("You are No this Restaurant's Manager")
                    .timeStamp(LocalDateTime.now())
                    .build();
        }
    }
//    @GetMapping
//    public ResponseEntity<Response<List<OrdersGetDto>>> getAllOrders() {
//        List<OrdersGetDto> orders = ordersService.getAllOrders();
//        Response<List<OrdersGetDto>> response = Response.<List<OrdersGetDto>>builder()
//                .timeStamp(LocalDateTime.now())
//                .statusCode(HttpStatus.OK.value())
//                .status(HttpStatus.OK)
//                .message("Orders retrieved successfully")
//                .data(orders)
//                .build();
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/last/{userId}")
    public LastOrderDto getLastOrderForUser(@PathVariable Long userId) {
        return ordersService.getLastOrderForUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<OrdersGetDto>> getOrderById(@PathVariable Long id) {
        OrdersGetDto orders = ordersService.getOrderById(id);
        Response<OrdersGetDto> response = Response.<OrdersGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Order retrieved successfully")
                .data(orders)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getOrdersForUser")
    public ResponseEntity<Response<List<OrdersGetDto>>> getOrdersForCurrentUser(HttpServletRequest request) {
        try {
            String jwtToken = jwtUtils.getJwtFromRequest(request);
            logger.debug("JWT token from request: {}", jwtToken);

            String jwtUserId = jwtService.extractId(jwtToken);
            logger.debug("Extracted JWT user ID: {}", jwtUserId);

            Long userId = Long.parseLong(jwtUserId);
            logger.debug("Converted JWT user ID to Long: {}", userId);

            List<OrdersGetDto> orders = ordersService.getOrdersForCurrentUser(userId);

            Response<List<OrdersGetDto>> response = Response.<List<OrdersGetDto>>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Orders retrieved successfully for the current user")
                    .data(orders)
                    .build();

            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            logger.error("Invalid user ID format in the JWT token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.<List<OrdersGetDto>>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Invalid user ID format in the JWT token")
                    .build());
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.<List<OrdersGetDto>>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred: " + e.getMessage())
                    .build());
        }
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<Response<OrdersGetDto>> createOrder(@RequestBody OrdersPostDto ordersPostDto,@RequestHeader("Authorization") String token) {
        Long userId= Long.valueOf(jwtService.extractId(token));
        OrdersGetDto createdOrder = ordersService.createOrder(ordersPostDto,userId);

        Response<OrdersGetDto> response = Response.<OrdersGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Order created successfully")
                .data(createdOrder)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<OrdersGetDto>> updateOrder(@PathVariable Long id, @RequestBody OrdersPutDto ordersPutDto) {

        OrdersGetDto updatedOrder = ordersService.updateOrder(id, ordersPutDto);

        Response<OrdersGetDto> response = Response.<OrdersGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Order updated successfully")
                .data(updatedOrder)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/reschedule")
    public ResponseEntity<Response<String>> rescheduleOrder(@PathVariable Long orderId, @RequestBody OrderRescheduleDto rescheduleDto) {
        boolean isDataChanged = ordersService.rescheduleOrder(orderId, rescheduleDto);

        String successMessage = isDataChanged ? "Order rescheduled successfully." : "No changes made to the order.";

        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message(successMessage)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Order deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
