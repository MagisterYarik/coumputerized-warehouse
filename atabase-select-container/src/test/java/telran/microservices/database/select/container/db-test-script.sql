delete from container;
delete from product;
insert into product (product_id, product_name, units, lv, discrete)
	values (500, 'Potato', 'kg', 20, false);
insert into container (container_id, product_product_id, product_capacity)
	values (1000, 500, 5000);