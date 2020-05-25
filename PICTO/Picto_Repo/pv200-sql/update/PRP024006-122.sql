alter table pv2qtcat add CATEGORY_FR varchar(255) not null;
alter table pv2qtcat add CATEGORY_EN varchar(255) not null;
commit;

update pv2qtcat set CATEGORY='SIGN', CATEGORY_FR='Signalisation (iso A)', CATEGORY_EN='Signalling (iso A)'  where ID=1;
update pv2qtcat set CATEGORY='BREAK', CATEGORY_FR='Freinage (iso B)', CATEGORY_EN='Braking (iso B)'  where ID=2;
update pv2qtcat set CATEGORY='VISIBLE', CATEGORY_FR='Visibilité (iso C)', CATEGORY_EN='Visibility (iso C)'  where ID=3;
update pv2qtcat set CATEGORY='CABIN', CATEGORY_FR='Cabine (iso D)', CATEGORY_EN='Cabin (iso D)'  where ID=4;
update pv2qtcat set CATEGORY='LOAD', CATEGORY_FR='Chargement (iso E)', CATEGORY_EN='Loading (iso E)'  where ID=5;
update pv2qtcat set CATEGORY='ENGINE', CATEGORY_FR='Moteur (iso F)', CATEGORY_EN='Engine (iso F)'  where ID=6;
update pv2qtcat set CATEGORY='FUEL', CATEGORY_FR='Carburant (iso G)', CATEGORY_EN='Fuel (iso G)'  where ID=7;
update pv2qtcat set CATEGORY='TRANS', CATEGORY_FR='Transmission (iso H)', CATEGORY_EN='Transmission (iso H)'  where ID=8;
update pv2qtcat set CATEGORY='TRAIN', CATEGORY_FR='Train roulant (iso I)', CATEGORY_EN='Undercarriage (iso I)'  where ID=9;
update pv2qtcat set CATEGORY='ADAS', CATEGORY_FR='ADAS iso J', CATEGORY_EN='ADAS iso J'  where ID=10;
update pv2qtcat set CATEGORY='SECURITY', CATEGORY_FR='Sécurité (iso K + L)', CATEGORY_EN='Security (iso K + L)'  where ID=11;
update pv2qtcat set CATEGORY='ELECRIC', CATEGORY_FR='Électricité (iso M)', CATEGORY_EN='Electricity (iso M)'  where ID=12;
update pv2qtcat set CATEGORY='COMMUN', CATEGORY_FR='Communication (iso N)', CATEGORY_EN='Communication (iso N)'  where ID=13;
update pv2qtcat set CATEGORY='VEHICLE', CATEGORY_FR='Véhicules (iso W)', CATEGORY_EN='Vehicles (iso W)'  where ID=14;
update pv2qtcat set CATEGORY='INTERFACE', CATEGORY_FR='IHM', CATEGORY_EN='HMI'  where ID=15;
update pv2qtcat set CATEGORY='NAVIG', CATEGORY_FR='Navigation', CATEGORY_EN='Navigation'  where ID=16;
update pv2qtcat set CATEGORY='MEDIA', CATEGORY_FR='Média', CATEGORY_EN='Media'  where ID=17;
update pv2qtcat set CATEGORY='APPWEB', CATEGORY_FR='Apps + web', CATEGORY_EN='Apps + web'  where ID=18;
update pv2qtcat set CATEGORY='MARK', CATEGORY_FR='Marquages spécifiques', CATEGORY_EN='Specific Markings'  where ID=19;

commit;



