/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jute.compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CSharpGenerator {
    private ArrayList<JRecord> mRecList;
    private final File outputDirectory;

    /** Creates a new instance of CSharpGenerator
     *
     * @param name possibly full pathname to the file
     * @param ilist included files (as JFile)
     * @param rlist List of records defined within this file
     * @param outputDirectory
     */
    CSharpGenerator(String name, ArrayList<JFile> ilist, ArrayList<JRecord> rlist,
            File outputDirectory)
     {
        this.outputDirectory = outputDirectory;
        mRecList = rlist;
    }

    /**
     * Generate C# code. This method only creates the requested file(s)
     * and spits-out file-level elements (such as include statements etc.)
     * record-level code is generated by JRecord.
     */
    void genCode() throws IOException {
        for (JRecord rec : mRecList) {
            rec.genCsharpCode(outputDirectory);
        }
    }
}
