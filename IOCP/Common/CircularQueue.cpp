#include "CircularQueue.h"
#include <Windows.h>

namespace COMMONLIB
{
	CircularQueue::CircularQueue()
	{
		memset(cRingBuf, 0, sizeof(cRingBuf));

		cpBufEnd = cRingBuf + sizeof(cRingBuf) - 1; //���� �Ѿ�� ���� üũ�ϱ� ���� �̸� ����� ��

		Clear();
	}

	CircularQueue::~CircularQueue()
	{
		
	}

	void CircularQueue::Clear()
	{
		cpBufFront = cRingBuf;
		cpBufRear = cRingBuf;
		dwDataSize = 0;
	}

	bool CircularQueue::IsBufferEmpty()
	{
		return (cpBufFront == cpBufRear);
	}

	bool CircularQueue::IsBufferFull()
	{
		return (dwDataSize == GetQueueSize());
	}

	bool CircularQueue::Push(const char* pData, int const nSize)
	{
		if (nSize > GetRemainQueueSize()) //ť ���� ���� ũ�� false
			return false;

		if (nSize <= (cpBufEnd - cpBufRear - 1))
		{
			//�ѹ��� �� ���� �ִ� ���
			memcpy(cpBufRear, pData, nSize);
			cpBufRear += nSize;
		}
		else
		{
			//(1) ���۳����� ����
			int nTempSize = cpBufEnd - cpBufRear;
			memcpy(cpBufRear, pData, nTempSize);
			cpBufRear = cRingBuf; //���� ���� ������

								  //(2) �����κ� �տ��� ����
			memcpy(cpBufRear, pData + nTempSize, nSize - nTempSize);
			cpBufRear += (nSize - nTempSize);
		}

		dwDataSize += nSize;

		return true;
	}

	bool CircularQueue::Pop(char* pData, int nSize)
	{
		if (nSize > dwDataSize) //ť�׵� ũ�⺸�� ū��� false
			return false;

		if (nSize <= (cpBufEnd - cpBufFront - 1))
		{
			//�ѹ��� �� ������ �ִ°��
			memcpy(pData, cpBufFront, nSize);
			cpBufFront += nSize;
		}
		else
		{
			//(1) ���۳����� �а�      
			int nTempSize = cpBufEnd - cpBufFront;
			memcpy(pData, cpBufFront, nTempSize);
			cpBufFront = cRingBuf; //���� ���� ������

								   //(2) ���ʺ��� �ٽ� �о�� �ϴ� ���
			memcpy(pData + nTempSize, cpBufFront, nSize - nTempSize);
			cpBufFront += (nSize - nTempSize);
		}

		dwDataSize -= nSize;

		return true;
	}

	long CircularQueue::GetQueueSize()
	{
		return RINGBUFSIZE + MAXPACKETSIZE - 1; //-1�� ����ť���� Full Check�� ���� ����
	}

	long CircularQueue::GetRemainQueueSize()
	{
		return GetQueueSize() - dwDataSize;
	}
}