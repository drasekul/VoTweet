package facade;

import java.util.List;

import model.Keyword;

public interface KeywordFacade {
	public String create(Keyword entity);

	public void edit(Keyword entity);

	public void remove(Keyword entity);

	public Keyword find(Object id);

	public List<Keyword> findAll();

	public List<Keyword> findRange(int[] range);

	public int count();
}
