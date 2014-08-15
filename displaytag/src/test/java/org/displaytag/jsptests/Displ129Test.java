/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.jsptests;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-129 - Partial list support with valuelist pattern.
 * @author Fabrizio Giustina
 * @version $Id: $
 */
public class Displ129Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-129.jsp";
    }

    /**
     * No exception when an invalid page is requested.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        ParamEncoder encoder = new ParamEncoder("table");
        String pageParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        request.setParameter(pageParameter, "2");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables in result.", 1, tables.length);
        Assert.assertEquals("Wrong number of rows in result.", 3, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        Assert.assertEquals("Wrong column header.", "Number", tables[0].getCellAsText(0, 0));
        Assert.assertEquals("Wrong column content.", "3", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Wrong column content.", "4", tables[0].getCellAsText(2, 0));

        TableCell headerCell = tables[0].getTableCell(0, 0);

        String cssClass = headerCell.getClassName();
        assertEqualsIgnoreOrder(
            "Wrong css attributes.",
            new String[]{"sortable", "sorted", "order2"},
            StringUtils.split(cssClass));

        WebLink[] headerLinks = headerCell.getLinks();
        Assert.assertEquals("Sorting link not found.", 1, headerLinks.length);
        WebLink sortingLink = headerLinks[0];
        assertEqualsIgnoreOrder(
            "Wrong parameters.",
            new String[]{"sort", "searchid", "dir", pageParameter},
            sortingLink.getParameterNames());

        HTMLElement pagebanner = response.getElementWithID("pagebanner");
        Assert.assertEquals("Wrong page banner", "10|3|4", pagebanner.getText());
        HTMLElement pagelinks = response.getElementWithID("pagelinks");
        Assert.assertEquals("Wrong page links", "1|[2]|3|4|5", pagelinks.getText());

    }

}