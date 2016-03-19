set title 'dist_nb_links_per_servers (AVG = 0.3350659736105558)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:2500]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

