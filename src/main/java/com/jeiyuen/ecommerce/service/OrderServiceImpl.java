package com.jeiyuen.ecommerce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import com.jeiyuen.ecommerce.exceptions.ApiException;
import com.jeiyuen.ecommerce.exceptions.ResourceNotFoundException;
import com.jeiyuen.ecommerce.model.Address;
import com.jeiyuen.ecommerce.model.Cart;
import com.jeiyuen.ecommerce.model.CartItem;
import com.jeiyuen.ecommerce.model.Order;
import com.jeiyuen.ecommerce.model.OrderItem;
import com.jeiyuen.ecommerce.model.Payment;
import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.OrderDTO;
import com.jeiyuen.ecommerce.payload.OrderItemDTO;
import com.jeiyuen.ecommerce.payload.OrderRequestDTO;
import com.jeiyuen.ecommerce.repository.AddressRepository;
import com.jeiyuen.ecommerce.repository.CartRepository;
import com.jeiyuen.ecommerce.repository.OrderItemRepository;
import com.jeiyuen.ecommerce.repository.OrderRepository;
import com.jeiyuen.ecommerce.repository.PaymentRepository;
import com.jeiyuen.ecommerce.repository.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private CartRepository cartRepository;
    private AddressRepository addressRepository;
    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private CartService cartService;
    private ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(CartRepository cartRepository,
            AddressRepository addressRepository,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            CartService cartService,
            ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public OrderDTO placeOrder(String emailId, String paymentMethod, OrderRequestDTO orderRequestDTO) {
        // Get User Cart
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "email", emailId);
        }

        Address address = addressRepository.findById(orderRequestDTO.getAddressId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Address", "addressId", orderRequestDTO.getAddressId()));

        // Create Order with Payment Details
        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted");
        order.setAddress(address);
        Payment payment = new Payment(
                paymentMethod,
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage(),
                orderRequestDTO.getPgName());
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);

        // Get Items from the Cart
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new ApiException("Cart is Empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);

        // Update product stock
        cart.getCartItems().forEach(item -> {
            int quantity = item.getQuantity();
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);

            // Clear the cart
            cartService.deleteProductFromCart(cart.getCartId(), item.getProduct().getProductId());
        });

        // Send back the order summary
        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        orderItems.forEach(item -> orderDTO.getOrderItems()
                .add(modelMapper.map(item, OrderItemDTO.class)));
        orderDTO.setAddressId(orderRequestDTO.getAddressId());
        return orderDTO;
    }

}
