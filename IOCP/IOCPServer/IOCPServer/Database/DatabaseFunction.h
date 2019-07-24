#pragma once
#include "Common_DB.h"
#include <WinSock2.h>
#include <mysql.h>
#include <string>

/*
	 *	Make a person	: rojae
	 *	Make a date		: 2019.07.24
	 *	Class name		: Common_DB
	 *	Class describe	: 데이터베이스에 접속하기 위한 클래스 생성
*/

namespace database {
	typedef struct DB_value {
		std::string id;
		std::string auth;
	}user_DB_value;

	class DatabaseManager {
	public:
		user_DB_value userDB;
		MYSQL *connection = NULL, conn;
		MYSQL_RES *sql_result;
		MYSQL_ROW sql_row;
		int query_stat;

	public:
		static void initDatabase();
		static bool connectDatabase();
		bool executeQuery(char *query);
		void closeDatabase();
		std::string getID();
		bool getAuth();
	};
}