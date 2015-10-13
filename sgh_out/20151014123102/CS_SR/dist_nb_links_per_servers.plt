set title 'dist_nb_links_per_servers (AVG = 0.39166666666666666)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:119]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

