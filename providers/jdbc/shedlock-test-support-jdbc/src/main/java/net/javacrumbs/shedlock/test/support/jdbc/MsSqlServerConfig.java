/**
 * Copyright 2009-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.shedlock.test.support.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MSSQLServerContainer;

public final class MsSqlServerConfig extends AbstractDbConfig {

    private MyMSSQLServerContainer mssql;
    private static final Logger logger = LoggerFactory.getLogger(MsSqlServerConfig.class);

    @Override
    protected void doStartDb() {
        mssql = new MyMSSQLServerContainer()
            .withLogConsumer(outputFrame -> logger.debug(outputFrame.getUtf8String()));
        mssql.start();
    }

    @Override
    protected void doShutdownDb() {
        mssql.stop();
    }

    public String getJdbcUrl() {
        return mssql.getJdbcUrl();

    }

    @Override
    public String getUsername() {
        return mssql.getUsername();
    }

    @Override
    public String getPassword() {
        return mssql.getPassword();
    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE shedlock(name VARCHAR(64) NOT NULL, lock_until datetime2 NOT NULL, locked_at datetime2 NOT NULL, locked_by VARCHAR(255) NOT NULL, PRIMARY KEY (name))";
    }

    @Override
    public String nowExpression() {
        return "SYSUTCDATETIME()";
    }

    private static class MyMSSQLServerContainer extends MSSQLServerContainer<MyMSSQLServerContainer> {
    }
}
