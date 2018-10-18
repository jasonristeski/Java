import java.util.Arrays;

public class MergeSort extends Thread
{
	private int[] data;
	private int left;
	private int right;
	private int mid;
	
	public MergeSort(int[] in, int start, int end)
	{
		data = in;
		left = start;
		right = end;
	}
	
	private void sort()
	{
		try
		{
			if(right > left)
			{
				mid = (right + left) /2 ;
				MergeSort leftSort = new MergeSort(data,left,mid);
				mid++;
				MergeSort rightSort = new MergeSort(data,mid,right);
				leftSort.start();
				rightSort.start();
				
				leftSort.join();
				rightSort.join();
				merge();
				
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void merge()
	{
		int[] temp = new int[data.length];
		int leftEnd = mid - 1;
		int tmp = left;
		int count = right - left+1;
		
		while((left <= leftEnd) && (mid <= right))
		{
			if(data[left] <= data[mid])
			{
				temp[tmp] = data[left];
				tmp++;
				left++;
			}
			else
			{
				temp[tmp] = data[mid];
				tmp++;
				mid++;
			}
		}
		
		while(left <= leftEnd)
		{
			temp[tmp] = data[left];
			left++;
			tmp++;
		}
		
		while(mid <= right)
		{
			temp[tmp] = data[mid];
			mid++;
			tmp++;
		}
		
		for(int i = 0; i < count;i++)
		{
			data[right] = temp[right];
			right --;
		}
	}
	
	public void run()
	{
		sort();
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			// args [0] = in.txt 
			
			FileIO file = new FileIO("in.txt");
			
			//FileIO file = new FileIO(args[0]);
			file.read();
			int[] data = file.getData();
			MergeSort sort = new MergeSort(data,0,data.length -1);
			sort.start();
		
			sort.join();
			file.write(data);
			System.out.println(Arrays.toString(data));
		}
		catch (InterruptedException e)
		{
			
			e.printStackTrace();
		}
	}
}
