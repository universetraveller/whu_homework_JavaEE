package cn.edu.whu.ProductManager.exception;
public class ProductException extends Exception{
	Long productId;
	int status;
	String msg;
	public ProductException(Long id, int status, String msg){
		super(msg);
		this.status = status;
		this.productId = id;
		this.msg = msg;
	}
	public int getStatus(){
		return this.status;
	}
	public Long getProductId(){
		return this.productId;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public void setProductId(Long id){
		this.productId = id;
	}
}
