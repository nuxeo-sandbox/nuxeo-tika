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
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

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
public class TestExtractMetadata implements ISamples {
	
    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    @Test
    public void extractMetadataFromDoc() throws OperationException, IOException  {
    	extractMetadata(DOC_EXAMPLE, DOC_EXAMPLE_METATADATA);		
    }

    @Test
    public void extractMetadataFromExcel() throws OperationException, IOException  {
    	extractMetadata(EXCEL_EXAMPLE, EXCEL_EXAMPLE_METATADATA);		
    }    

    @Test
    public void extractMetadataFromPdf() throws OperationException, IOException  {
    	extractMetadata(PDF_EXAMPLE, PDF_EXAMPLE_METATADATA);		
    }        

    @Test
    public void extractMetadataFromPowerPoint() throws OperationException, IOException  {
    	extractMetadata(POWERPOINT_EXAMPLE, POWERPOINT_EXAMPLE_METATADATA);		
    }     

    @Test
    public void extractMetadataFromPng() throws OperationException, IOException  {
    	extractMetadata(PNG_EXAMPLE, PNG_EXAMPLE_METATADATA);		
    }    
    
    @Test
    public void extractMetadataFromTif() throws OperationException, IOException  {
    	extractMetadata(TIF_EXAMPLE, TIF_EXAMPLE_METATADATA);		
    }        
    
    
    private void extractMetadata(String fileName, String json) throws OperationException, IOException {
  		File file = FileUtils.getResourceFileFromContext(fileName);
  		Blob blob = new FileBlob(file);	
  		
  		OperationContext ctx = new OperationContext(session);
  		ctx.setInput(blob);
  		Blob output = (Blob) automationService.run(ctx, ExtractMetadata.ID);

  		assertNotNull(output);
  		
  		String txt = output.getString();
  		
  		assertNotNull(txt);  	
  		assertEquals(json, txt);  		
      }   
}
