/*
 *  Copyright (C) 2009 WaveMaker Software, Inc.
 *
 *  This file is part of the WaveMaker Server Runtime.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wavemaker.runtime.data.sample.orahr;

// Generated Aug 19, 2007 9:06:50 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Regions generated by hbm2java
 */
@SuppressWarnings({ "serial", "unchecked" })
public class Regions implements java.io.Serializable {

    private BigDecimal regionId;

    private String regionName;

    private Set countrieses = new HashSet(0);

    public Regions() {
    }

    public Regions(BigDecimal regionId) {
        this.regionId = regionId;
    }

    public Regions(BigDecimal regionId, String regionName, Set countrieses) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.countrieses = countrieses;
    }

    public BigDecimal getRegionId() {
        return this.regionId;
    }

    public void setRegionId(BigDecimal regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Set getCountrieses() {
        return this.countrieses;
    }

    public void setCountrieses(Set countrieses) {
        this.countrieses = countrieses;
    }

}
