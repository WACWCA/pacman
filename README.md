<h1>Pacman</h1>
<img src="images/pacman1.PNG" alt="image1"/>


<h2>Maze generation</h2>
<p>The algorithm for generating the mazes was inspired by that described on <a href="https://shaunlebron.github.io/pacman-mazegen/">Shaun Lebron's Github</a>, which additionally defined constraints of traditional pac man mazes
</p>
<ul>
  <li></li>
  
  <li>Map is 28x31 tiles</li>
  <li>Paths are only 1 tile thick</li>
  <li>No sharp turns (i.e. intersections are separated by atleast 2 tiles)</li>
  <li>There are 1 or 2 tunnels</li>
  <li>No dead-ends</li>
  <li>Only I, L, T, or + wall shapes are allowed, including the occasional rectangular wall</li>
  <li>Any non-rectangular wall pieces must only be 2 tiles thick</li>
</ul>
<p>
  <img src="images/mazematch1.PNG" width="400" />
  <img src="images/mazematch2.PNG" width="400" /> 
</p>
<p>
  <img src="images/randmaze1.PNG" width="250" />
  <img src="images/randmaze2.PNG" width="250" /> 
  <img src="images/randmaze3.PNG" width="250" />
</p>

