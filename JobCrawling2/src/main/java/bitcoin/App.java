package bitcoin;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        
    	try {
			
    		BitCoinAPI_bitthumb bitThumb = null;
        	
        	int count = 0;
        	
        	while (true) {
        	
        		Thread.sleep(1000);
        		if(count%10 == 0){
        			System.out.println("Time Count == "+ count +"seconds");
        			bitThumb = new BitCoinAPI_bitthumb();
        			bitThumb.start();
        		}
        		count++;
        	}
    		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("에러가 발생하여 시스템을 종료합니다");
			System.exit(1);

		}
    	
    }
}
