package com.simplemall.micro.serv.prd.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.simplemall.micro.serv.prd.pay.AliH5Pay;
import com.simplemall.micro.serv.prd.service.impl.PrdServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.simplemall.micro.serv.common.bean.product.PrdInfo;
import com.simplemall.micro.serv.prd.service.IPrdService;

import javax.servlet.http.HttpServletResponse;

@RestController
//@Controller
@RequestMapping("/prd")
public class PorudctController {
	private Logger logger = LoggerFactory.getLogger(PorudctController.class);
	@Autowired
	IPrdService prdService;

	RateLimiter rateLimiter = RateLimiter.create(10);

	private AliH5Pay h5Pay = new AliH5Pay();

	@RequestMapping(value = "payPage", method = RequestMethod.GET)
	public void getH5PayPage(HttpServletResponse response) {
		logger.info("getH5PayPage{}");

		String form = h5Pay.testH5Pay();

		response.setContentType("text/html;charset=utf-8" );
		try {
			response.getWriter().write(form);//直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	;



	}

	/**
	 * get a product info
	 * 
	 * @param prdId
	 * @return
	 */
	@RequestMapping(value = "{prdId}", method =  RequestMethod.GET)
	public PrdInfo getPorudctById(@PathVariable String prdId) throws IOException {
		logger.info("开始获取商品详情{}:" + prdId);

		if (StringUtils.equals(prdId,"5") ) {
			logger.info("开始休眠" );
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		PrdInfo prdInfo =  prdService.getProductById(prdId);
		if(prdInfo != null && StringUtils.isNotEmpty(prdInfo.getLabelName()))
		{
			logger.info("获取商品成功 " + prdId );
			return  prdInfo;
		}
		else
		{
			logger.info("无此商品 " + prdId );
			throw new IOException("prdId: " +prdId + " not exist");
		}

	}

	/**
	 * 获取商品列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public List<PrdInfo> list() {
		long lcur= System.currentTimeMillis();
		if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
			System.out.println("短期无法获取令牌，真不幸，排队也瞎排 tm:" + (System.currentTimeMillis() - lcur));
			return null;
		}
		System.out.println("购买成功 tm:" + (System.currentTimeMillis() - lcur));
	/*	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		return prdService.queryPrdList();
	}

}
