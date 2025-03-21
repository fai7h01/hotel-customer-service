-- Mock data for rooms
INSERT INTO rooms (room_number, room_type, price_per_night, capacity, floor_number, is_available,
                   insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, is_deleted) VALUES
-- SINGLE rooms
('101', 'SINGLE', 79.99, 1, 1, true, now(), 1, now(), 1, false),
('102', 'SINGLE', 79.99, 1, 1, true, now(), 1, now(), 1, false),

-- DOUBLE rooms
('103', 'DOUBLE', 99.99, 2, 1, true, now(), 1, now(), 1, false),
('201', 'DOUBLE', 99.99, 2, 2, true, now(), 1, now(), 1, false),
('202', 'DOUBLE', 109.99, 2, 2, true, now(), 1, now(), 1, false),

-- TWIN rooms
('203', 'TWIN', 109.99, 2, 2, false, now(), 1, now(), 1, false),
('301', 'TWIN', 119.99, 2, 3, true, now(), 1, now(), 1, false),

-- TRIPLE rooms
('302', 'TRIPLE', 129.99, 3, 3, true, now(), 1, now(), 1, false),
('303', 'TRIPLE', 129.99, 3, 3, true, now(), 1, now(), 1, false),

-- QUAD rooms
('401', 'QUAD', 149.99, 4, 4, true, now(), 1, now(), 1, false),

-- QUEEN rooms
('402', 'QUEEN', 159.99, 2, 4, true, now(), 1, now(), 1, false),
('501', 'QUEEN', 169.99, 2, 5, true, now(), 1, now(), 1, false),

-- KING rooms
('502', 'KING', 189.99, 2, 5, false, now(), 1, now(), 1, false),
('601', 'KING', 199.99, 2, 6, true, now(), 1, now(), 1, false),

-- STUDIO rooms
('602', 'STUDIO', 219.99, 3, 6, true, now(), 1, now(), 1, false),

-- SUITE rooms
('701', 'SUITE', 299.99, 4, 7, true, now(), 1, now(), 1, false),
('801', 'SUITE', 349.99, 4, 8, true, now(), 1, now(), 1, false),

-- PENTHOUSE
('901', 'PENTHOUSE', 599.99, 6, 9, true, now(), 1, now(), 1, false);