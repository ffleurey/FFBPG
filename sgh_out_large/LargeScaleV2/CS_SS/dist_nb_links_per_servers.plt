set title 'dist_nb_links_per_servers (AVG = 0.26229508196721313)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:1890]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

