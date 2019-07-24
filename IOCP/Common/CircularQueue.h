#pragma once


namespace COMMONLIB
{
#define RINGBUFSIZE 10
#define MAXPACKETSIZE 1024

	class CircularQueue
	{
	protected:
		char  cRingBuf[RINGBUFSIZE + MAXPACKETSIZE]; //���Ÿ�����
		char  *cpBufFront, *cpBufRear; //���Ÿ����� ��������
		char  *cpBufEnd; //������ ����ġ
		long  dwDataSize; //ť�׵� ������ ũ��

	public:
		CircularQueue();
		~CircularQueue();

	public:
		void Clear();
		bool IsBufferEmpty(); //������� üũ
		bool IsBufferFull(); //full ���� üũ
		bool Push(const char* pData, const int nSize);
		bool Pop(char* pData, int nSize);
		long GetQueueSize();  //ť ��üũ��
		long GetRemainQueueSize(); //ť�� ������ �ִ� ���� ũ��

		long GetDataSize() { return dwDataSize; }
	};
}