#include <iostream>
#include "DatabaseFunction.h"

#pragma comment(lib, "libmysql.lib")


using namespace database;

DatabaseManager manager;

void DatabaseManager::initDatabase() {
	std::cout << "MySQL client version:" << mysql_get_client_info() << '\n';
	// 초기화
	mysql_init(&(manager.conn));
}
bool DatabaseManager::connectDatabase() {
	// DB 연결
	manager.connection = mysql_real_connect(&(manager.conn), DB_HOST, DB_USER, DB_PASS, DB_NAME, MYSQL_PORT, (char *)NULL, 0);
	if (manager.connection == NULL)
	{
		fprintf(stderr, "Mysql connection error : %s", mysql_error(&(manager.conn)));
		return false;
	}
	else {
		std::cout << "MySQL Connection Success! " << '\n';

		//한글사용을위해추가.
		mysql_query(manager.connection, "set session character_set_connection=utf-8;");
		mysql_query(manager.connection, "set session character_set_results=utf-8;");
		mysql_query(manager.connection, "set session character_set_client=utf-8;");

		std::cout << "complete Setting utf-8 encoding" << '\n';
		return true;
	}
}

bool DatabaseManager::executeQuery(char *query) {
	// Select 쿼리문
	query_stat = mysql_query(manager.connection, query);
	if (query_stat != 0)
	{
		fprintf(stderr, "Mysql query error : %s", mysql_error(&conn));
		return false;
	}

	// 결과출력
	sql_result = mysql_store_result(manager.connection);
	while ((sql_row = mysql_fetch_row(sql_result)) != NULL)
	{
		userDB.auth = sql_row[0];
	}
	mysql_free_result(sql_result);

	if (userDB.auth == "1")
		return true;
	else
		return false;
}

std::string DatabaseManager::getID() {
	return userDB.id;
}
bool DatabaseManager::getAuth() {
	if (userDB.auth == "1") {
		return true;
	}
	else {
		return false;
	}
}
void DatabaseManager::closeDatabase() {
	// DB 연결닫기
	mysql_close(connection);
}
