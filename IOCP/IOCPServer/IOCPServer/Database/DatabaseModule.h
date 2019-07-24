#include "DatabaseFunction.h"

/*
	 *	Make a person	: rojae
	 *	Make a date		: 2019.07.24
	 *	Class name		: DatabaseModule
	 *	Class describe	: 데이터베이스 접속 및 인증
*/

class DatabaseModule {
public:
	void StartDB();
	bool checkAuth(char * query);
};