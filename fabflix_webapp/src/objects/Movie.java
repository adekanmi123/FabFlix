package objects;

import java.util.ArrayList;
import java.util.Comparator;

public class Movie 
{
	private int id;
	private String title;
	private int year;
	private String director;
	private String banner_url;
	private String trailer_url;
	private String photo_url;
	private ArrayList<Genre> genres;
	private ArrayList<Star> stars;
	
	public Movie(int id, String title, int year, String director, String banner_url, String trailer_url) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner_url = banner_url;
		this.trailer_url = trailer_url;
		this.genres = new ArrayList<Genre>();
		this.stars = new ArrayList<Star>();
	}
	
	public Movie(int id, String title, int year, String director,
			String banner_url, String trailer_url, ArrayList<Genre> genres,
			ArrayList<Star> stars) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.genres = genres;
		this.stars = stars;
		this.banner_url = banner_url;
		this.trailer_url = trailer_url;
	}
	
	public Movie(String photo_url, int id, String title) {
	   	this.setPhoto_url(photo_url);
	   	this.id = id;
	    this.title = title;
	}
	
	
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public int getYear() {
		return year;
	}
	public String getDirector() {
		return director;
	}
	public String getBanner_url() {
		return banner_url;
	}
	public String getTrailer_url() {
		return trailer_url;
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public ArrayList<Star> getStars() {
		return stars;
	}

	public String getPhoto_url() {
		return photo_url;
	}


	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}
	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}


	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}

	public void setStars(ArrayList<Star> stars) {
		this.stars = stars;
	}
	
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	
	
	public void addGenre(Genre genre)
	{
		genres.add(genre);
	}
	
	public void addStar(Star star)
	{
		stars.add(star);
	}
	
	
	
	public static Comparator<Movie> orderByTitleAscending() {
		return new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return (o1.getTitle().compareTo(o2.getTitle()));
			}
		};
	}

	public static Comparator<Movie> orderByTitleDescending() {
		return new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return (o2.getTitle().compareTo(o1.getTitle()));
			}
		};
	}

	public static Comparator<Movie> orderByYearAscending() {
		return new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				if (o1.getYear() == o2.getYear()) {
					return 0;
				}
				else if (o1.getYear() > o2.getYear()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	}

	public static Comparator<Movie> orderByYearDescending() {
		return new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				if (o1.getYear() == o2.getYear()) {
					return 0;
				}
				else if (o1.getYear() < o2.getYear()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	}
}
