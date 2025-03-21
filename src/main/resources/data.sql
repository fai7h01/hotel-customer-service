-- Mock data for users (corrected)
INSERT INTO users ( first_name, last_name, email, password, phone, role,
                   insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, is_deleted) VALUES
-- Admin user
( 'John', 'Admin', 'admin@hotel.com', '123456789', '123-456-7890',
 'ADMIN', now(), 1, now(), 1, false),

-- Customer users
( 'Sarah', 'Johnson', 'sarah@example.com', '123456789', '234-567-8901',
 'CUSTOMER', now(), 1, now(), 1, false),

( 'Michael', 'Smith', 'michael@example.com', '123456789', '345-678-9012',
 'CUSTOMER', now(), 1, now(), 1, false),

( 'Emily', 'Jones', 'emily@example.com', '123456789', '456-789-0123',
 'CUSTOMER', now(), 1, now(), 1, false),

( 'David', 'Wilson', 'david@example.com', '123456789', '567-890-1234',
 'CUSTOMER', now(), 1, now(), 1, false);




-- Mock data for rooms
INSERT INTO rooms (room_number, room_type, price_per_night, capacity, floor_number,
                   insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, is_deleted) VALUES
-- SINGLE rooms
('101', 'SINGLE', 79.99, 1, 1, now(), 1, now(), 1, false),
('102', 'SINGLE', 79.99, 1, 1, now(), 1, now(), 1, false),

-- DOUBLE rooms
('103', 'DOUBLE', 99.99, 2, 1, now(), 1, now(), 1, false),
('201', 'DOUBLE', 99.99, 2, 2,  now(), 1, now(), 1, false),
('202', 'DOUBLE', 109.99, 2, 2,  now(), 1, now(), 1, false),

-- TWIN rooms
('203', 'TWIN', 109.99, 2, 2,  now(), 1, now(), 1, false),
('301', 'TWIN', 119.99, 2, 3,  now(), 1, now(), 1, false),

-- TRIPLE rooms
('302', 'TRIPLE', 129.99, 3, 3,  now(), 1, now(), 1, false),
('303', 'TRIPLE', 129.99, 3, 3,  now(), 1, now(), 1, false),

-- QUAD rooms
('401', 'QUAD', 149.99, 4, 4,  now(), 1, now(), 1, false),

-- QUEEN rooms
('402', 'QUEEN', 159.99, 2, 4,  now(), 1, now(), 1, false),
('501', 'QUEEN', 169.99, 2, 5,  now(), 1, now(), 1, false),

-- KING rooms
('502', 'KING', 189.99, 2, 5, now(), 1, now(), 1, false),
('601', 'KING', 199.99, 2, 6,  now(), 1, now(), 1, false),

-- STUDIO rooms
('602', 'STUDIO', 219.99, 3, 6, now(), 1, now(), 1, false),

-- SUITE rooms
('701', 'SUITE', 299.99, 4, 7,  now(), 1, now(), 1, false),
('801', 'SUITE', 349.99, 4, 8, now(), 1, now(), 1, false),

-- PENTHOUSE
('901', 'PENTHOUSE', 599.99, 6, 9, now(), 1, now(), 1, false);


-- Mock data for bookings
INSERT INTO bookings ( user_id, room_id, check_in_date, check_out_date, number_of_guests, total_price,
                       status, payment_status, insert_date_time, insert_user_id,
                       last_update_date_time, last_update_user_id, is_deleted) VALUES
-- Booking for SINGLE room (101)
( 4, 1, '2025-03-01', '2025-03-31', 1, 239.97,
  'CONFIRMED', 'PAID', now(), 1, now(), 1, false),

-- Booking for SINGLE room (102)
( 5, 2, '2025-03-01', '2025-03-31', 1, 239.97,
  'CONFIRMED', 'PAID', now(), 1, now(), 1, false),

-- Booking for DOUBLE room (103)
( 1, 3, '2025-03-01', '2025-03-21', 2, 299.97,
  'CONFIRMED', 'PAID', now(), 1, now(), 1, false),

-- Booking for TRIPLE room (302)
( 2, 8, '2025-03-15', '2025-03-21', 3, 649.95,
  'CONFIRMED', 'PENDING', now(), 1, now(), 1, false),

-- Booking for SUITE room (701)
( 3, 16, '2025-04-15', '2025-04-21', 4, 1199.96,
  'PENDING', 'CANCELLED', now(), 1, now(), 1, false),

( 2, 10, '2025-04-01', '2025-04-30', 4, 1199.96,
    'CONFIRMED', 'PAID', now(), 1, now(), 1, false);
