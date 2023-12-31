delete from order_head;
insert into order_head (order_id, status, container_id, product_id, 
						demand, order_date, delivery_date)
values 
	(80085, 1, 1000, 1518, 75.5, CURRENT_DATE, DATEADD('DAY', 7, CURRENT_DATE)),
	(575, 1, 1000, 1518, 35.5, CURRENT_DATE, DATEADD('DAY', 7, CURRENT_DATE)),
	(7, 20, 2000, 1518, 79.5, CURRENT_DATE, DATEADD('DAY', 7, CURRENT_DATE)),
	(8, 22, 2000, 1518, 91.5, CURRENT_DATE, DATEADD('DAY', 7, CURRENT_DATE));