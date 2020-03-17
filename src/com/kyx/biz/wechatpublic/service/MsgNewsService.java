/*
 * FileName：MsgNewsService.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.kyx.biz.wechatpublic.service;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.model.MsgNews;


public interface MsgNewsService {

	 MsgNews getById(String id);
	
	 RetInfo addMoreNews(MsgNews news);
	 
	 GrdData getWebNewsListByPage(MsgNews searchEntity, Pager pager);

	 int updateMediaId(MsgNews msgNews);
	 
	 int updateTitle(MsgNews msgNews);
	 
	 int delete(Integer id);
}
