package com.wwls.common.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class JDResource {
	private static final String JingDongProductEntity ="JingDongProductEntity";
	private static final String JingDongResourceImage="JingDongResourceImage";
	private static final String JingDongResourcePrice="JingDongResourcePrice";
	private static final String JingDongResourceStock = "JingDongResourceStock";
	protected static Logger logger = LoggerFactory.getLogger(JDResource.class);

//	@Resource(name="crudMongoDao")
//	private CrudMongoDao crudMongoDao;
//	@Resource(name="mcResourceService")
	

}
