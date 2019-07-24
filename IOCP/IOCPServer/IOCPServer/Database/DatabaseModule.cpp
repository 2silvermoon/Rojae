#include "Database/DatabaseModule.h"

using namespace database;

DatabaseManager dba;

void DatabaseModule::StartDB() {
	dba.initDatabase();
	dba.connectDatabase();
}

bool DatabaseModule::checkAuth(char * query) {
	return dba.executeQuery(query);
}