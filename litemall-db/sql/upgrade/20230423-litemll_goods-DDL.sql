alter table `litemall_goods`
add column `tag_price` decimal(10,2) DEFAULT '100000.00' COMMENT '吊牌价格';
alter table `litemall_goods`
add column `wholesale_price` decimal(10,2) DEFAULT '100000.00' COMMENT '批发价格';