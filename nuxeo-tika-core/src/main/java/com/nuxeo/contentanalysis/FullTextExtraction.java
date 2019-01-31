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

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;

/**
 *
 */
@Operation(id=FullTextExtraction.ID, category=Constants.CAT_DOCUMENT, label="Extracts text from any type of document", description="Describe here what your operation does.")
public class FullTextExtraction {

    public static final String ID = "Document.FullTextExtraction";

    @Context
    protected CoreSession session;

	@Context
	protected OperationContext ctx;    

    @Param(name = "xpath", description = "xpath to the blob that must analyzed", required = false, values = { "file:content" })
    protected String xpath;	

	@OperationMethod
	public DocumentModel run(DocumentModel doc) {

		if (doc != null) {		
			Blob b = (Blob) doc.getPropertyValue(xpath);
			run(b);
		}
		return doc;
	}    
    
    @OperationMethod
    public Blob run(Blob blob) {
    	Blob output = null;
    	
		if (blob != null) {
			File f = blob.getFile();
			if (f != null) {
				Tika tika = new Tika();
				String str = null;
				try {
					str = tika.parseToString(f);
				} catch (IOException | TikaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(str != null) {
					output = new StringBlob(str);					
				}				
			}						
		}
		
		return output;
    }
}
