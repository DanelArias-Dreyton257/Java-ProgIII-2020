package examen.ext201702;

public class Counter {
	private int count = 0;
	
	public Counter() {
		
	}
	public Counter(int a) {
		setCount(a);
	}
	
	public void inc() {
		count++;
	}
	public void inc(int a) {
		count+=a;
	}
	public void dec() {
		count--;
	}
	public void dec(int a) {
		count-=a;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Counter other = (Counter) obj;
		if (count != other.count)
			return false;
		return true;
	}
	
}
