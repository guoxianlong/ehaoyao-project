/**
 * 运单主表
 */
CREATE TABLE `waybill_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_flag` varchar(10) NOT NULL COMMENT '订单标识',
  `order_number` varchar(30) NOT NULL COMMENT '订单号',
  `waybill_source` varchar(15) NOT NULL COMMENT '运单来源',
  `waybill_number` varchar(30) NOT NULL COMMENT '运单号',
  `waybill_status` char(3) NOT NULL COMMENT '运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投',
  `is_writeback` int(2) DEFAULT NULL COMMENT '回写订单中心物流信息  0:回写失败 1:回写成功',
  `order_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单时间',
  `last_time` datetime DEFAULT NULL COMMENT '运单信息最后更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `Index_waybill_info_waybill_number` (`waybill_number`),
  KEY `Index_waybill_info_is_writeback` (`is_writeback`),
  KEY `Index_waybill_info_order_flag_number` (`order_flag`,`order_number`) USING BTREE,
  KEY `Index_waybill_info_waybill_source` (`waybill_source`) USING BTREE,
  KEY `Index_waybill_info_waybill_status` (`waybill_status`) USING BTREE,
  KEY `Index_waybill_info_last_time` (`last_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=479999 DEFAULT CHARSET=utf8;

/**
 * 运单明细表
 */
CREATE TABLE `waybill_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `waybill_source` varchar(15) NOT NULL COMMENT '运单来源',
  `waybill_number` varchar(30) NOT NULL COMMENT '运单号',
  `waybill_status` char(3) NOT NULL COMMENT '当前运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投',
  `waybill_time` datetime DEFAULT NULL COMMENT '当前运单跟踪时间',
  `waybill_content` varchar(200) DEFAULT NULL COMMENT '当前运单跟踪信息',
  `waybill_address` varchar(200) DEFAULT NULL COMMENT '配送地址',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `Index_waybill_detail_waybill_time` (`waybill_time`),
  KEY `Index_waybill_detail_waybill_number` (`waybill_number`),
  KEY `Index_waybill_detail_waybill_source` (`waybill_source`) USING BTREE,
  KEY `Index_waybill_detail_waybill_status` (`waybill_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3922845 DEFAULT CHARSET=utf8