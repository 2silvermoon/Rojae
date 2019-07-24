
#include "LIB_SESSIONPOOL.h"

using namespace COMMONLIB;

LIB_SESSIONPOOL::LIB_SESSIONPOOL()
{
	this->m_Lock.Lock();
	{
		for ( WORD iter = 0; iter < SESSION_NUM; iter++ )
			m_SessionBuffer[iter] = new LIB_SESSIONDATA(iter);
	}
	this->m_Lock.UnLock();
}

LIB_SESSIONPOOL::~LIB_SESSIONPOOL()
{
	this->m_Lock.Lock();
	{
		for ( WORD iter = 0; iter < SESSION_NUM; iter++ )
			SAFE_DELETE_POINT(m_SessionBuffer[iter]);

		m_mapPlayer.clear();
	}
	this->m_Lock.UnLock();
}

LIB_SESSIONDATA* LIB_SESSIONPOOL::CreateSession()
{
	LIB_SESSIONDATA* pPlayer = NULL;

	this->m_Lock.Lock();
	{
		for ( int iter = 0; iter < SESSION_NUM; iter++ )
		{
			if ( TRUE != m_SessionBuffer[iter]->GetReference() )	// empty session �̸�
			{
//				m_SessionBuffer[iter]->ClearSession();	// Ȥ�ó� �𸣴ϱ� �� ���� ������ ����.
				m_SessionBuffer[iter]->SetReference();	// session ��������� �ٲ۴�.
				pPlayer = m_SessionBuffer[iter];
				break;
			}
		}
	}
	this->m_Lock.UnLock();

	return pPlayer;
}


LIB_SESSIONDATA* LIB_SESSIONPOOL::FindSession(const int index)
{
	if (index < 0 || index >= SESSION_NUM) {
		return nullptr;
	}

	return m_SessionBuffer[index];
}

const VOID LIB_SESSIONPOOL::InsertSession(LIB_SESSIONDATA* pSession)
{
	this->m_Lock.Lock();
	{
		m_mapPlayer.insert(PLAYERMAP::value_type(pSession->GetSessionID(), pSession));
	}
	this->m_Lock.UnLock();
}

const WORD LIB_SESSIONPOOL::GetSessionSize()
{
	WORD	wSessionSize = 0;

	this->m_Lock.Lock();
	{
		wSessionSize = (WORD)m_mapPlayer.size();
	}
	this->m_Lock.UnLock();

	return wSessionSize;
}

const VOID LIB_SESSIONPOOL::DeleteSession(LIB_SESSIONDATA* pSession)
{
	PLAYERMAP::iterator iter;

	this->m_Lock.Lock();
	{
		for ( iter = m_mapPlayer.begin(); iter != m_mapPlayer.end(); iter++ )
		{
			LIB_SESSIONDATA* pFindSession = iter->second;
			if ( pSession )
			{
				if ( (pFindSession == pSession) && (pFindSession->GetSessionID() == pSession->GetSessionID()) )
				{
					pFindSession->ClearSession();
					m_mapPlayer.erase(iter);
					break;
				}
			}
		}
	}
	this->m_Lock.UnLock();
}