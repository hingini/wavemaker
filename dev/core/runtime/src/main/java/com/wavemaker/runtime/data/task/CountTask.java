/*
 *  Copyright (C) 2007-2011 VMWare, Inc. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wavemaker.runtime.data.task;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.wavemaker.runtime.data.QueryOptions;
import com.wavemaker.runtime.data.Task;

/**
 * @author Simon Toens
 */
public class CountTask extends SearchTask implements Task, DefaultRollback {

    @Override
    public Object run(Session session, String dbName, Object... input) {

        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("Need Class instance");
        }

        QueryOptions options = getQueryOptions(input);

        Criteria criteria = getCritieria(session, input[0], options, dbName);

        criteria.setProjection(Projections.rowCount());

        return criteria.uniqueResult();
    }

    @Override
    protected boolean addOrder() {
        return false;
    }

    @Override
    public String getName() {
        return "Built-in Num Pages Task";
    }

}