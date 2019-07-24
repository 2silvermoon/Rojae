
#pragma once

#ifndef __LIB_LOCK_H__
#define __LIB_LOCK_H__

namespace COMMONLIB
{
	/*
	 *	Make a person	: �����
	 *	Make a date		: 2007.02.09
	 *	Class name		: LIB_LOCK
	 *	Class describe	: CRITICAL_SECTION �� �̿��� �����ڿ� ó�����
	*/

	class LIB_LOCK
	{
	public :
		LIB_LOCK()			{}
		virtual ~LIB_LOCK()	{}
	public :
		VOID virtual Lock() = 0;
		VOID virtual UnLock() = 0;
	};

	class LIB_CRITICALLOCK : public LIB_LOCK
	{
	private :
		CRITICAL_SECTION	m_CS;
	public :
		LIB_CRITICALLOCK()			{	InitializeCriticalSection(&m_CS);	}
		~LIB_CRITICALLOCK()			{	DeleteCriticalSection(&m_CS);		}
	public :
		inline VOID Lock()		{	EnterCriticalSection(&m_CS);	}
		inline VOID UnLock()	{	LeaveCriticalSection(&m_CS);	}
	};
}

#endif	// __LIB_LOCK_H__