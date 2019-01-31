/** (C) Copyright 2013-2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * 		Joaquín Garzón <jgarzon@nuxeo.com>
 */
package com.nuxeo.contentanalysis;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.impl.blob.FileBlob;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("com.nuxeo.contentanalysis.nuxeo-tika-core")
public class TestDetectFileType implements ISamples{
			
    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    @Test
    public void detectFileTypeDoc() throws OperationException {
    	detectFileType(DOC_EXAMPLE, "application/msword");		
    }

    @Test
    public void detectFileTypeExcel() throws OperationException {
    	detectFileType(EXCEL_EXAMPLE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");		
    }    

    @Test
    public void detectFileTypePdf() throws OperationException {
    	detectFileType(PDF_EXAMPLE, "application/pdf");		
    }        

    @Test
    public void detectFileTypePowerPoint() throws OperationException {
    	detectFileType(POWERPOINT_EXAMPLE, "application/vnd.openxmlformats-officedocument.presentationml.presentation");		
    }       
    
    private void detectFileType(String fileName, String expectedType) throws OperationException {
		File file = FileUtils.getResourceFileFromContext(fileName);
		Blob blob = new FileBlob(file);	
		
		OperationContext ctx = new OperationContext(session);
		ctx.setInput(blob);
		automationService.run(ctx, DetectFileType.ID);

		assertEquals(expectedType, ctx.get(DetectFileType.CTX_FILE_TYPE));
    }    
    
}
