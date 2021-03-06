/**
 * Copyright (c) 2004-2011 Wang Jinbao(Julian Wong), http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.ralasafe.servlet;

import org.ralasafe.RalasafeException;
import org.ralasafe.entitle.UserCategory;
import org.ralasafe.entitle.UserCategoryManager;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;

public class UserCategoryPolicyDesignAction extends AbstractPolicyDesignAction {
	
	public String getPolicyHandlerAttributeKey( HttpServletRequest req ) {
		int id=WebUtil.getIntParameter( req, "id", 0 );
		return "_$ralasafeUserCategory_" + id;
	}

	public AbstractPolicyDesignHandler createPolicyHandler( HttpServletRequest req ) {
		int id=WebUtil.getIntParameter( req, "id", 0 );
		
		UserCategoryManager manager=WebUtil.getUserCategoryManager( req );
		UserCategory uc=manager.getUserCategory( id );
		
		org.ralasafe.db.sql.xml.UserCategoryType xmlUc;
		try {
			xmlUc = org.ralasafe.db.sql.xml.UserCategory.unmarshal(new StringReader(uc.getXmlContent()));
		} catch (Exception e) {
			throw new RalasafeException(e);
		}
		
		return new UserCategoryPolicyDesignHandler( manager, xmlUc );
	}
}
