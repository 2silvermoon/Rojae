#include "ServiceManager.h"
#include "../../Common/Protocol.h"
#include "Database/DatabaseModule.h"
#include <string>

using namespace NETWORKLIB;

const BOOL ServiceManager::RecvCS_AUTH_LOGIN_REQ(LIB_SESSIONDATA* pSession)
{
	DatabaseModule databaseModule;
	TCHAR	Buffer[MAX_BUFFER];
	static UINT snCount = 0;	// 이 static 변수도 테스트 때문에 남긴거라서 삭제해도 됩니다

	body_CS_AUTH_LOGIN_REQ* body = (body_CS_AUTH_LOGIN_REQ*)(Buffer + SIZE_HEADER);

	memcpy(&Buffer, &pSession->m_SocketCtx.recvContext->Buffer, sizeof(body_CS_AUTH_LOGIN_REQ) + SIZE_HEADER);

	int id = body->id;
	int pw = body->pw;

	printf("클라이언트로 전송받은 값 id : %d || pw : %d || visitCount : %d\n", id, pw, ++snCount);
	
	m_Log.EventLog(2, "Access Database Server", "Release OK ");

	databaseModule.StartDB();

	std::string str = "SELECT auth FROM test WHERE id =";
	str.append((std::to_string(id)));
	char * query = new char[str.size() + 1];
	std::copy(str.begin(), str.end(), query);
	query[str.size()] = '\0'; // 스트링 끝에 0을 추가해주는 거 잊지 마세요

	if (databaseModule.checkAuth(query)) {
		m_Log.EventLog(2, "[Permission]", "Client is member of us");
	}
	else {
		m_Log.EventLog(2, "[Denial]", "Client is not member");
	}

	delete[] query;

	this->RecvCS_AUTH_LOGIN_ACK(pSession);

	return 1;
}