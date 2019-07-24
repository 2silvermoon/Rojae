
#pragma once

#ifndef __LIB_PROFILE_H__
#define __LIB_PROFILE_H__

#include "Common.h"

namespace COMMONLIB
{
	/*
	 *	Make a person	: �����
	 *	Make a date		: 2006.10.07
	 *	Class name		: LIB_PROFILE
	 *	Class describe	: INI ���Ϸκ��� �ؽ�Ʈ�� READ �ϰų� WRITE �� �����ϰ��ϴ� Ŭ����
	*/

	class LIB_PROFILE
	{
	public :
		LIB_PROFILE();
		~LIB_PROFILE();
	private :
		TCHAR	m_FilePath[MAX_STRING];
	public :
		const VOID	SetFilePath(LPCTSTR strPath);
		const VOID	WriteToINI(LPCTSTR strSection, LPCTSTR strKey, int nValue);
		const VOID	WriteToINI(LPCTSTR strSection, LPCTSTR strKey, LPCTSTR strValue);
		const VOID	GetStringFromINI(LPCTSTR strSection, LPCTSTR strKey, LPSTR strResult);
		const DWORD	GetDWORDFromINI(LPCTSTR strSection, LPCTSTR strKey);
		const BOOL	DeleteINI();
	};
}

#endif	// __LIB_PROFILE_H__