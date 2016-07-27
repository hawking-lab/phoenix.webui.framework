/**
 * http://surenpi.com
 */
package org.suren.autotest.web.framework.selenium.action;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.suren.autotest.web.framework.core.Locator;
import org.suren.autotest.web.framework.core.action.CheckAble;
import org.suren.autotest.web.framework.core.ui.Element;
import org.suren.autotest.web.framework.selenium.locator.SeleniumTextLocator;
import org.suren.autotest.web.framework.selenium.strategy.SearchStrategyUtils;
import org.suren.autotest.web.framework.selenium.strategy.ZoneSearchStrategy;

/**
 * @author suren
 * @date Jul 27, 2016 1:00:30 PM
 */
@Component
public class SeleniumCheck implements CheckAble
{
	private static final Logger logger = LoggerFactory.getLogger(SeleniumCheck.class);
	
	@Autowired
	private SearchStrategyUtils		searchStrategyUtils;
	
	@Autowired
	private SeleniumTextLocator textLocator;
	@Autowired
	private ZoneSearchStrategy zoneSearchStrategy;

	@Override
	public void checkByText(Element element, String text)
	{
		WebElement webEle = searchStrategyUtils.findStrategy(WebElement.class, element).search(element);
		if(webEle == null)
		{
			logger.error(String.format("can not found element byText [%s].", text));
			return;
		}
		
		List<Locator> locatorList = element.getLocatorList();
		locatorList.add(textLocator);
		
		try
		{
			WebElement itemWebEle = zoneSearchStrategy.search(element);
			if(itemWebEle != null)
			{
				itemWebEle.click();
			}
		}
		finally
		{
			locatorList.remove(textLocator);
		}
	}

}