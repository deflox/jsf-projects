-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 192.168.3.150    Database: reecepty
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `additionalitems`
--

DROP TABLE IF EXISTS `additionalitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `additionalitems` (
  `additionalItemId` int(11) NOT NULL AUTO_INCREMENT,
  `circleId` int(11) NOT NULL,
  `additionalItemName` varchar(50) NOT NULL,
  `amount` int(11) NOT NULL,
  `translationId` int(11) NOT NULL COMMENT 'To get the name.',
  PRIMARY KEY (`additionalItemId`),
  KEY `fk_AdditionalItems_Circles1_idx` (`circleId`),
  KEY `fk_TranslationId_TranslationId_idx` (`translationId`),
  CONSTRAINT `fk_translationId` FOREIGN KEY (`translationId`) REFERENCES `amountunittranslation` (`amountUnitTranslationId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `additionalitems`
--

LOCK TABLES `additionalitems` WRITE;
/*!40000 ALTER TABLE `additionalitems` DISABLE KEYS */;
INSERT INTO `additionalitems` VALUES (66,100,'Blue Meth',50,3),(68,1,'Cola',1,1),(69,39,'Deo',10,1);
/*!40000 ALTER TABLE `additionalitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `amountunittranslation`
--

DROP TABLE IF EXISTS `amountunittranslation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amountunittranslation` (
  `amountUnitTranslationId` int(11) NOT NULL AUTO_INCREMENT,
  `amountUnit` int(11) NOT NULL,
  `amountUnitSize` int(11) NOT NULL,
  `amountUnitTranslationName` varchar(45) NOT NULL,
  PRIMARY KEY (`amountUnitTranslationId`),
  KEY `fk_amountUnit_idx` (`amountUnit`),
  KEY `fk_amountUnit_2_idx` (`amountUnit`),
  CONSTRAINT `fk_amountUnit_2` FOREIGN KEY (`amountUnit`) REFERENCES `units` (`unitId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amountunittranslation`
--

LOCK TABLES `amountunittranslation` WRITE;
/*!40000 ALTER TABLE `amountunittranslation` DISABLE KEYS */;
INSERT INTO `amountunittranslation` VALUES (1,3,1,'Piece'),(2,1,15,'Tablespoon'),(3,1,1000,'Kilogram'),(4,2,6,'Teaspoon'),(5,2,15,'Tablespoon'),(6,2,100,'Deciliter'),(7,2,1000,'Liter'),(8,1,5,'Teaspoon');
/*!40000 ALTER TABLE `amountunittranslation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attempts`
--

DROP TABLE IF EXISTS `attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attempts` (
  `attemptIp` varchar(15) NOT NULL DEFAULT '',
  `count` int(11) NOT NULL,
  `entry` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`attemptIp`),
  UNIQUE KEY `attemptId_UNIQUE` (`attemptIp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attempts`
--

LOCK TABLES `attempts` WRITE;
/*!40000 ALTER TABLE `attempts` DISABLE KEYS */;
/*!40000 ALTER TABLE `attempts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingredients` (
  `ingredientId` int(11) NOT NULL AUTO_INCREMENT,
  `recipeId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`ingredientId`),
  KEY `fk_Ingredients_Recipes1_idx` (`recipeId`),
  KEY `fk_Ingredients_Products1_idx` (`productId`),
  CONSTRAINT `fk_Ingredients_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ingredients_Recipes1` FOREIGN KEY (`recipeId`) REFERENCES `recipes` (`recipeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (25,64,45,2),(26,64,51,250),(27,64,50,1),(28,64,47,2),(29,64,48,250),(30,64,46,2),(31,64,44,4),(32,64,43,1),(33,64,49,2),(34,65,52,2),(35,65,44,6),(36,65,43,1),(37,65,46,2),(38,65,51,500),(39,65,54,200),(40,65,57,500),(41,65,58,1),(42,65,55,280),(43,65,53,1),(44,65,56,800),(45,66,43,1),(46,66,64,1),(47,66,60,1800),(48,66,65,50),(49,66,62,1),(50,66,59,1000),(51,66,46,1),(52,66,63,1),(53,66,52,1),(54,66,61,1),(55,67,70,1),(56,67,66,8),(57,67,62,1),(58,67,67,1500),(59,67,68,200),(60,67,69,100),(61,67,71,1),(62,68,74,1),(63,68,2,160),(64,68,46,1),(65,68,73,5),(66,68,62,1),(67,68,3,10),(68,68,75,20),(69,68,43,1),(70,68,56,100),(71,68,72,250),(72,68,61,10),(73,69,62,1),(74,69,77,15),(75,69,1,5),(76,69,76,4),(77,69,66,250),(78,69,78,1),(79,70,87,1),(80,70,81,1),(81,70,80,2),(82,70,86,2),(83,70,82,1),(84,70,51,125),(85,70,85,1),(86,70,84,1),(87,70,79,3),(88,70,83,1),(89,70,61,2),(90,70,62,1),(91,71,95,1),(92,71,89,1),(93,71,94,4),(94,71,46,1),(95,71,92,1),(96,71,43,1),(97,71,90,3),(98,71,93,1),(99,71,45,1),(100,71,88,1),(101,71,91,2),(102,71,56,100);
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitchens`
--

DROP TABLE IF EXISTS `kitchens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kitchens` (
  `kitchenid` int(11) NOT NULL,
  `kitchenName` varchar(45) NOT NULL,
  PRIMARY KEY (`kitchenid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitchens`
--

LOCK TABLES `kitchens` WRITE;
/*!40000 ALTER TABLE `kitchens` DISABLE KEYS */;
INSERT INTO `kitchens` VALUES (1,'Italian'),(2,'American'),(3,'Chinese'),(4,'Française'),(5,'Swiss');
/*!40000 ALTER TABLE `kitchens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `productId` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) NOT NULL,
  `amountUnit` int(11) NOT NULL,
  PRIMARY KEY (`productId`),
  KEY `fk_amountUnit_idx` (`amountUnit`),
  CONSTRAINT `fk_amountUnit` FOREIGN KEY (`amountUnit`) REFERENCES `units` (`unitId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Salt',2),(2,'Water',2),(3,'Sugar',2),(4,'Light corn syrup',2),(8,'Blue food coloring',2),(9,'Lemon flavoring',2),(43,'olive oil',5),(44,'smoked bacon',3),(45,'red onions',3),(46,'garlic',4),(47,'carrot',3),(48,'mushrooms',1),(49,'celery',3),(50,'thymian',3),(51,'beef',1),(52,'rosemary',3),(53,'onion',3),(54,'red wine',2),(55,'sun-dried tomatos',1),(56,'plum tomatos',1),(57,'dried spaghetti',1),(58,'parmesan cheese',3),(59,'côte de boeuf',1),(60,'peeled potatoes',1),(61,'sea salt',3),(62,'black pepper',3),(63,'unsalted butter',3),(64,'herbs',3),(65,'cider',2),(66,'potatoes',3),(67,'raclette cheese',1),(68,'buendnerfleisch',1),(69,'cornishons',1),(70,'onions',3),(71,'paprica',3),(72,'flour',1),(73,'yeast',1),(74,'fresh basil',7),(75,'mozzarella',1),(76,'cornstarch',1),(77,'butter',1),(78,'bradwurst',3),(79,'crackers',3),(80,'parsley',3),(81,'egg',3),(82,'lettuce',3),(83,'tomatos',3),(84,'red onion',3),(85,'gherkins',3),(86,'fresh burger buns',3),(87,'dijon musterd',3),(88,'aubergines',3),(89,'courgettes',3),(90,'red pepper',3),(91,'ripe tomatoes',3),(92,'basil',3),(93,'thymie',3),(94,'balsamic',1),(95,'lemon',3);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipes` (
  `recipeId` int(11) NOT NULL AUTO_INCREMENT,
  `kitchenId` int(11) NOT NULL,
  `recipeName` varchar(45) NOT NULL,
  `shortDesc` varchar(200) NOT NULL,
  `calories` int(11) NOT NULL,
  `fullDesc` mediumtext NOT NULL,
  `titleImage` varchar(150) NOT NULL,
  `duration` int(11) NOT NULL,
  PRIMARY KEY (`recipeId`),
  KEY `index_kitchens` (`kitchenId`),
  KEY `index_recipeName` (`recipeName`),
  KEY `index_calories` (`calories`),
  KEY `index_duration` (`duration`),
  CONSTRAINT `fk_Recipes_Kitchens1` FOREIGN KEY (`kitchenId`) REFERENCES `kitchens` (`kitchenid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (64,1,'Lasagne','The good old lasagne. Everyone will like it!',430,'<!--StartFragment--><p>Make the Bolognese sauce by adding a good lug of olive oil to a large casserole pan over a medium heat. Add the bacon and fry until golden, then add the onions, garlic, carrots, mushrooms and celery, then add the thyme leaves and cook for around 10 minutes, or until softened and lightly coloured, stirring regularly.</p><p>Add the minced meat and tinned tomatoes followed by 1 tin of water, the wine, and a good pinch of salt and pepper and the basil stalks. Bring to the boil, then reduce the heat to low and simmer with a lid on for around 1 hour, stirring every now and again. Remove the lid, turn the heat to high and cook for a further 20 to 30 minutes, or until thickened and reduced. Stir regularly and add a splash of water to loosen, if needed.</p><p>Meanwhile, fill a large pan with salted boiling water, add a drizzle of olive oil, then blanch your pasta sheets for 3 to 4 minutes – it’s best to cook them in batches. Drain the sheets then carefully pat them dry with kitchen paper and put to one side until needed.&nbsp;</p><p>Preheat the oven to 200°C/400°F/gas 6. Add the onion, milk, parsley, nutmeg and peppercorns to a pan over a medium heat and gently bring to the boil, then strain into a jug. Melt the butter in pan over a medium-low heat, then mix in the flour adding and stirring in one splash of milk at a time until you have a smooth white sauce. Bring to the boil, then simmer for a couple of minutes before removing from the heat and stirring through most of the Parmesan. Season to taste.</p><p>Remove the Bolognese from the heat, then tear in and stir through the basil leaves. Have a taste of the sauce and season with a little more salt and pepper if needed.</p><p>To assemble the lasagne, spoon a third of the Bolognese into the bottom of an ovenproof dish (roughly 25cm x 30cm), then follow with a layer of lasagne sheets and a third of your white sauce. Add another layer of Bolognese and repeat the process twice more, finishing with a layer of white sauce and the remaining Parmesan. Drizzle with olive oil, cover with tin foil, then place in the oven for 20 minutes. Remove the foil and cook for a further 30 minutes on the grill/fan setting (at the same temperature), or until golden brown and bubbling at the edges. Serve with a lovely fresh green salad.</p><!--EndFragment-->','http://cdn-1.guidecucina.it/o/orig/ricette-bimby-lasagne-al-forno_c46797c47990446ab3253f8ef4a41cf7.jpg',120),(65,1,'Spaghetti','A great introduction to pasta for kids – loads of fun to eat, and a brilliant base for adding all kinds of other fresh ingredients.',556,'<!--StartFragment--><p>Preheat your oven to 180ºC/350ºF/gas 4. Put a casserole pan on a medium heat, add a splash of olive oil then cook the bacon, rosemary, garlic and onion for about 5 minutes, stirring now and then, until soft. Add the mince and break apart any lumps with a wooden spoon. Let it cook for a couple of minutes until starting to brown then pour in the red wine.&nbsp;</p><p>Let that bubble away while you drain and blitz the sun-dried tomatoes in a food processor. Add them to the mince with the tinned tomatoes. Stir well and break the plum tomatoes apart a little. Cover with a lid then cook in the hot oven for 1 hour. Remove the lid after 30 minutes, and if it looks a little dry, add a splash of water to help it along.&nbsp;</p><p>About 10 minutes before the time is up, cook the spaghetti according to packet instructions. Drain, reserving a mugful of cooking water, then return the spaghetti to the hot pan with a few spoons of Bolognese, a good grating of Parmesan and a drizzle of extra virgin olive oil. Mix it about to coat the spaghetti and to stop it becoming claggy, loosening with a splash of cooking water if needed. Divide the spaghetti between your plates or bowls, add a good spoonful of Bolognese to each one then shave over a little Parmesan before serving.</p><!--EndFragment-->','http://www.venicecafechicago.com/wp-content/uploads/2014/07/venicea01.jpg',75),(66,2,'Steak','For the perfect steak and the most mind-blowing roast potatoes you’ll ever taste, this is it!',706,'<!--StartFragment--><p>Around 1 hour 30 minutes before you’re ready to start, remove the meat from the fridge and leave aside to come up to room temperature.</p><p>Preheat the oven to 200ºC/400ºF/gas 6. Place a large roasting tray in the oven to preheat. Meanwhile, slice the potatoes into rough 5cm chunks, then parboil in a pan of salted boiling water for around 12 minutes. Drain and return to the pan, shake to chuff them up, then leave to steam dry. Carefully remove the tray from the oven, add a good splash of olive oil, the butter and rosemary sprigs. Separate and bash the garlic cloves, then scatter into the tray of potatoes. Gently toss to coat, then season with salt and place in the oven for 45 minutes to 1 hour, or until golden and crisp.&nbsp;</p><p>Meanwhile, add the flavoured vinegar ingredients to a small plastic water spritzer, then set aside for later.</p><p>Make a herb brush by tying the woody herbs to the end of a wooden spoon with string. Preheat an ovenproof frying pan over a high heat to get it nice and hot – you want to do this around 25 minutes before the potatoes are ready. Add a splash of olive oil, then season the steak well and add to the pan, fat side-down, for a few minutes so the fat renders out. Sear the steak for around 8 minutes, or until browned all over, turning and basting with the herb brush every minute or so, then place in the oven for around 15 minutes for medium, or cook to your liking, turning halfway.</p><p>Transfer to a board, drizzle with the juices from the pan and leave to rest for around 3 minutes. When the roast potatoes are a few minutes away from being ready, spritz with the flavoured vinegar – don’t add too much, you want it to be subtle. Return to the oven for 30 seconds, then lightly squash with a fish slice. Spritz with a little more flavoured vinegar and return to the oven for a final few minutes.</p><p>Slice up the steak on a board and sprinkle with a little salt. Add a little extra virgin olive oil to the resting juices, then drizzle over the steak and serve with a nice salad, the vinegary roasties and a dollop of mustard, if you like.</p><!--EndFragment-->','http://www.characters.ca/wp-content/uploads/2015/05/steak.jpg',90),(67,5,'Raclette','The Raclette dining experience is one in which each individual can create their own unique and personalized meal.  Let your taste buds guide you on finding a culinary delight.',500,'<!--StartFragment--><p>Wash potatoes and boil in a pot filled with salted water for about 20 min. Leave the skin on!&nbsp;Test with a knife if the potatoes are done. Keep warm until ready to use in an insulated potatoe basket. In the meantime remove the rind of the cheese and cut into 1/16\" thick slices using an adjustable wire slicer. Arrange gherkins, onions, and Buenderfleisch&nbsp;on a platter and set aside until required.&nbsp;Turn raclette&nbsp;on to begin to heat up (allow for at least 5 minutes before using). For Raclette grills: Each guest takes a slice of cheese, places it in their pan and slides it under the raclette grill to melt. It takes approximately 2 minutes to melt to a creamy consistency and 3 minutes for a more crispier top. In the meantime take a potato, place onto your plate and cut it into a few pieces, remove the pan from under the grill once it\'s reached its preferred consistency and hold the pan onto its side to scrape the cheese out, using your wooden spatula.&nbsp;For Raclette melter,&nbsp;each guest prepares potatoes and side dishes on their plates. When the cheese starts melting on the wheel, scrape the cheese onto the plate.&nbsp;Season to taste with freshly ground pepper and paprika.</p><!--EndFragment-->','http://stationedingermany.com/wp-content/uploads/2013/12/Raclette.jpg',30),(68,1,'Pizza','Trust me, once you see how simple and tasty this pizza dough recipe is you won\'t want takeaways.',484,'<!--StartFragment--><p>This is a really simple method for pizza dough and a great place to start if you\'ve never made your own bread before. If you can find semolina flour, it gives the dough an authentic flavour and texture. But if you can\'t find it, strong white bread flour will also work.</p><p><strong>To make the dough:</strong></p><p>1. Pile the flour and salt on to a clean surface and make an 18cm well in the centre. Add your yeast and sugar to the lukewarm water, mix up with a fork and leave for a few minutes, then pour into the well.</p><p>2. Using a fork and a circular movement, slowly bring in the flour from the inner edge of the well and mix into the water. It will look like thick porridge. Continue to mix, bringing in all the flour. When the dough comes together and becomes too hard to mix with your fork, flour your hands and begin to pat it into a ball.</p><p>3. Knead the dough by rolling it backward and forward, using your left hand to stretch the dough toward you and your right hand to push the dough away from you at the same time. Repeat this for 10 minutes, until you have a smooth, springy, soft dough.</p><p>4. Place the dough in a lightly greased bowl. Cover with a kitchen towel or plastic wrap and let double in size for about 45 minutes.</p><p>To make the sauce:</p><p>5. Peel and finely slice the garlic. Pick the basil leaves and discard the stalks. Heat a saucepan on a medium-low heat and add a splash of olive oil and the garlic. Cook gently until the garlic starts to turn golden, then add most of the basil leaves, the tomatoes, and a good pinch of salt and pepper. Cook gently for around 20 minutes, or until smooth, mashing the tomatoes up with a wooden spoon as it cooks.</p><p>6. When done, have a little taste, and season again if needed.</p><p><strong>To make the pizza:</strong></p><p>7. Divide the dough in two. Wrap one half in plastic wrap and freeze for another batch (see Jamie\'s tips above). With the other half, divide the dough into 4 balls. Flour and cover each ball with plastic wrap, and let it rest for about 15 minutes. This will make it easier to roll it thinly.</p><p>8. Take a piece of the dough, dust your surface and the dough with a little flour or semolina, and roll it out into a rough circle about 0.5cm thick. Tear off an appropriately sized piece of tin foil, rub it with olive oil, dust it well with flour or semolina and place the pizza base on top. Continue doing the same with the other pieces and then, if you dust them with a little flour, you can pile them up into a stack, cover them with cling film and put them in the fridge</p><p>9. When you\'re ready to cook them, preheat your oven to 250°C/500°F/gas 9.</p><p>10. Put two of the rolled-out dough rounds onto each of two oiled baking sheets. At this stage you can apply your topping. Smear the tomato sauce over the base of your pizzas and spread it out to the edges. Tear over the mozzarella and scatter with the remaining basil leaves. Drizzle with a tiny bit of extra virgin olive oil and add a pinch of salt and pepper. If you can, cook the pizzas on a piece of granite in your conventional oven – if not, do them one by one on pieces of tin foil directly on the bars of the oven shelf, towards the bottom of the oven (If you\'re going to cook your pizzas on the bars of the oven, make sure they\'re not too big – otherwise they\'ll be difficult to manoeuvre). Cook for 7 to 10 minutes, until the pizzas are golden and crispy.</p><p>Topping ideas</p><p>When it comes to topping a pizza, the only thing you need to remember is: less is more. Keep your combinations simple and don\'t overload it with toppings so it has a chance to cook through. Try adding your favourite cold meats like Parma ham or salami, or slice up things like mushrooms, olives, courgettes or cooked potatoes and add them. You can also use leftovers – shredded roast pork or chicken would both work well. Have a play and find your favourite combos. Serve with a lovely green salad.</p><!--EndFragment-->','https://i.kinja-img.com/gawker-media/image/upload/bifqk4sdl9mggfbcv9vf.jpg',80),(69,5,'Bratwurst mit Rösti','The good old swiss recipe. Yet simple but absolutely delicious!',500,'<p>Run potatoes through the large shredding disk of a food processor. (If using a box grater, shred potatoes lengthwise so you are left with long shreds.).</p><p>Place potatoes in a large bowl and fill with cold water. Using hands, swirl to remove excess potato starch, then drain in strainer.</p><p>Transfer half of potatoes from strainer to the center of a clean kitchen towl. Gather ends together and twist as tightly as possible to expel maximum moisture.</p><p>Wipe bowl dry. Transfer potatoes to bowl and repeat process with remaining potatoes.</p><p>Sprinkle salt, cornstarch, and pepper to taste over potatoes. Using hands or fork, toss ingredients together until well blended.</p><p>Melt half of the butter (2 tablespoons) in a 10-inch nonstick skillet over medium heat. When foaming subsides, add potato mixture and spread into even layer. Cover and cook 6 minutes. Remove cover and, using spatula, gently press potatoes down to form round cake. Cook, occasionally pressing on potatoes to shape into uniform round cake, until bottom is deep golden brown, 4 to 6 minutes longer.</p><p>Shake skillet to loosen roesti and slide onto large plate. Add remaining 2 tablespoons butter to skillet and swirl to coat pan. Invert roesti onto second plage and slide it, browned side up, back into skillet. Cook, occasionally pressing down on cake, until bottom is well browned, 7-9 minutes. Remove pan from heat and allow cake to cool in pan for 5 minutes. Transfer roesti to cutting board, cut into 4 pieces, and serve immediately.</p>','https://media.hotelleriesuisse.ch/eyebasealbum.data/bilder/1024/424/00044550_m.jpg',25),(70,2,'Hamburger','Use this really tasty, easy homemade burger recipe as a base and tweak with your favourite toppings.',476,'<!--StartFragment--><p><strong>To make your burger:</strong></p><p>Wrap the crackers in a tea towel and smash up until fine, breaking up any big bits with your hands, and put them into a large bowl.</p><p>Finely chop the parsley, including the stalks. Add the parsley, mustard, if using, and minced beef to the bowl. Crack in the egg and add a good pinch of salt and pepper.</p><p>With clean hands, scrunch and mix everything up well. Divide into 6 and pat and mould each piece into a roundish shape about 2cm thick. Drizzle the burgers with oil, put on a plate, cover and place in the fridge until needed (this helps them to firm up).</p><p><strong>To cook your burger:</strong></p><p>Preheat a large griddle or frying pan for about 4 minutes on a high heat. Turn the heat down to medium.</p><p>Place the burgers on the griddle or in the pan and use a spatula to lightly press down on them, making sure the burger is in full contact. Cook them to your liking for 3 or 4 minutes on each side – you may need to cook them in two batches.</p><p><strong>To serve your burger:</strong></p><p>Wash and dry a few small lettuce leaves, tearing up the larger ones. Slice the tomatoes. Peel and finely slice the red onion. Slice the gherkins lengthways as finely as you can. Place all this on a platter and put in the middle of the table with plates, cutlery, ketchup and drinks.</p><p>Remove your burgers to another plate and carefully wipe your pan or griddle clean with kitchen paper.</p><p>Halve your burger buns and lightly toast them on the griddle or in the pan. Also great with a chopped salad.</p><!--EndFragment-->','http://www.womenshealth.de/food/gesunde-rezepte/wp-content/uploads/2011/10/sh_selbstgemachte_hamburger_80x462_144664049-530x306.jpg',40),(71,4,'Ratatouille','This humble vegetable stew is a super-healthy, hearty supper for any night of the week.',211,'<!--StartFragment--><p>Prep your ingredients before you start – peel and cut the onions into wedges, then peel and finely slice the garlic. Trim the aubergines and courgettes, deseed the peppers and chop into 2.5cm chunks. Roughly chop the tomatoes. Pick the basil leaves and set aside, then finely slice the stalks.</p><p>Heat 2 tablespoons of oil in a large casserole pan or saucepan over a medium heat. Add the chopped aubergines, courgettes and peppers (you may need to do this in batches) and fry for around 5 minutes, or until golden and softened, but not cooked through. Spoon the veg into a large bowl.</p><p>To the pan, add the onion, garlic, basil stalks and thyme leaves with another drizzle of oil, if needed. Fry for 10 to 15 minutes, or until softened and golden. Return the cooked veg to the pan and stir in the fresh and tinned tomatoes, the balsamic and a good pinch of sea salt and freshly ground black pepper.&nbsp;</p><p>Mix well, breaking up the tomatoes with the back of a spoon. Cover the pan and simmer over a low heat for 30 to 35 minutes, or until reduced, sticky and sweet. Tear in the basil leaves, finely grate in the lemon zest and adjust the seasoning, if needed. Serve with a hunk of bread or steamed rice.</p><!--EndFragment-->','http://www.bonappetit.com/wp-content/uploads/2012/07/grilled-ratatouille-salad.jpg',75),(72,1,'Cevapcici','One of the most popular balkan food.',500,'<p>I don\'t know how to prepare this meal. I\'m really sorry.</p>','http://s3-media1.fl.yelpcdn.com/bphoto/GfNMlpK9K4en5hPwyxVlew/o.jpg',10),(73,2,'Test hallo Welt','Test',5,'<p>&lt;script&gt;alert(\"Hello World\");&lt;/script&gt;</p>','Test',11);
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suggestions`
--

DROP TABLE IF EXISTS `suggestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suggestions` (
  `suggestionId` int(11) NOT NULL AUTO_INCREMENT,
  `circleId` int(11) NOT NULL,
  `recipeId` int(11) NOT NULL,
  `date` date NOT NULL,
  `isBought` bit(1) NOT NULL,
  `isSelected` bit(1) NOT NULL,
  PRIMARY KEY (`suggestionId`),
  KEY `fk_Days_Recipes1_idx` (`recipeId`),
  CONSTRAINT `fk_Days_Recipes1` FOREIGN KEY (`recipeId`) REFERENCES `recipes` (`recipeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suggestions`
--

LOCK TABLES `suggestions` WRITE;
/*!40000 ALTER TABLE `suggestions` DISABLE KEYS */;
INSERT INTO `suggestions` VALUES (87,13,65,'2015-09-29','\0','\0'),(96,93,69,'2015-09-30','\0',''),(98,97,65,'2015-09-30','\0','\0'),(99,97,70,'2015-09-30','\0','\0'),(101,100,70,'2015-09-30','\0','\0'),(102,100,65,'2015-10-01','\0',''),(106,1,67,'2015-09-30','\0',''),(107,1,70,'2015-09-30','\0',''),(108,1,66,'2015-09-30','\0',''),(109,1,65,'2015-10-02','\0','\0'),(111,38,69,'2015-09-30','\0',''),(113,38,67,'2015-10-03','\0','\0'),(114,39,65,'2015-10-01','\0',''),(116,39,64,'2015-10-01','\0','\0'),(117,39,66,'2015-10-04','\0',''),(118,39,69,'2015-10-05','\0',''),(119,39,67,'2015-10-05','\0','\0'),(120,13,65,'2015-09-30','\0','');
/*!40000 ALTER TABLE `suggestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `units` (
  `unitId` int(11) NOT NULL AUTO_INCREMENT,
  `unitName` varchar(45) NOT NULL,
  PRIMARY KEY (`unitId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'gram'),(2,'milliliter'),(3,'piece'),(4,'gloves'),(5,'bottle'),(6,'knob'),(7,'springs');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `circleId` int(11) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `addable` bit(1) NOT NULL,
  `trusted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_Users_Familys_idx` (`circleId`),
  KEY `email_INDEX` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (13,13,'leo.rudin@gmx.ch','$2a$12$c5o7rCKQaQWJLpMAmX5bYuIRGQUEBlWX45XmsAjciFA8asRmCmT76','Leo','Rudin','',''),(20,13,'max@musterman.com','$2a$12$An/Mu5tk2saJBt14hQ80nudIZO66ZYuH.Xy9grl8l1R0rHn/IM5vu','Max','Musterman','\0',''),(21,1,'henry.heinrich@henry.ch','$2a$12$5oFObsLDAibyl2jKPzfdIOH4ktQdiZPxL.33tMaT2kKz9a2ZW7LmC','Henry','Heinrich','','\0'),(22,22,'willi.fisk@fisk.fisk','$2a$12$uVWmcVzdafLHgmB/JU4Gv.EcXoRlYc4njGooypPEKVXCHE0FUvHe2','Wilson','Fisk','','\0'),(23,1,'milton@roth.xxx','$2a$12$oaHJ7i3NsuQ19ONyRR8Olul67WmDs74PirAjR1vScmOUgqW3gfIG6','Milton','Roth','','\0'),(24,24,'leo@rudin.cc','$2a$12$1hU3E3E0DLBMMLiSahcsEeudpL32PnTODKXsGXr2X.ZxtSbpsEaBy','Leo','Rudin','',''),(25,25,'aids@fuckme.com','$2a$12$wqVo4VHtYrGevnOzlwYEXuJ8AWRjwy7Mq8DudgzWKvlN26nExmwoG','Hoffe du stirbst','an Aids','','\0'),(26,92,'max@musterman.comff','$2a$12$PCXDxuzcLYtt2YdFWLwQheuZRqoBwdvMXEECyvldjl5.uia7xSUp6','dfhfgh','hggfhjgjh','','\0'),(27,27,'max@musterman.comdf','$2a$12$IncaNsVLyiHNwda/DTnTrO/uTWpTZn0L1ZKTt6cY.sCdAlIpFJ1gq','Testdfg','Mustermandfg','','\0'),(28,28,'max@musterman.comd','$2a$12$EvEqQnAH98mcxYnBFYHxvOrRM3RvLC/D1IwCJO7TEWPTijJcF8EWm','Maxdfg','Mustermandfg','','\0'),(29,93,'max@musterman.comx','$2a$12$QkFOwDRidzRDHXE1s7SxPOFYyxHyuB55c8oTNgpmeL6Mp7T4s4Rzi','Maxdsf','Mustermansdf','','\0'),(30,97,'mroth.mr7@gmail.com','$2a$12$K7331phT8.F7Y0eRd3FwYe/SOPw6vpAmEywGJkG/GXiVSEWoEG3uS','Milton','Roth','','\0'),(31,100,'dejan@bogdanovic.com','$2a$12$JdSm8SGWjRhu9m7PzPl8U.fw16hwS2BwD3L.n4ChL8f7hEzFPPgNm','Dejan','Bogdanovic','','\0'),(32,13,'leor13@gmx.ch','$2a$12$ZkkPT23y.OPD7WsDaIhmouGUJvcMAAbJYryBmwzj0lw1e3vdLRhvS','Dejan','Muster','\0','\0'),(33,1,'testcase2@testcase.com','$2a$12$VRW56N.NjAw0IQLr8eUbGuAxj8KXUvEWUXyuXS0w5/E8Ox7GNlBze','Hans','Muster','\0','\0'),(36,34,'dfgdfg@sdf.ccf','$2a$12$RMO50qhwnKoxlCPgv2.ajOGZvCW6BV0tZLIkKwaM/tAmLJwt6s2um','dsfgf','dfg','','\0'),(37,37,'dfgdfg@sdf.ccd','$2a$12$2MxhHiePbALDGxQJGe5e6OV1cnP7CQbXfY17WZehH1ozRUNl9w51C','dfgdfg','dfgdgggg','','\0'),(38,38,'max@wildli.cb','$2a$12$2KnYO841S5CWW.N.C6EEX.PCQrbU60CajGyNCC2wgo7us/QEUSSw2','Max','Wildli','','\0'),(39,39,'hans@muster.com','$2a$12$GTMrVs7WMUVhWh.OljvC8OEgvTaUna4Z/dRAetw.LCt3kFuq7zUES','Hans','Muster','','\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `reecepty`.`users_BEFORE_INSERT` BEFORE INSERT ON `users` FOR EACH ROW
BEGIN

        SET NEW.circleId = (SELECT userId FROM users ORDER BY userId DESC LIMIT 1) + 1;
   
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `votes` (
  `suggestionId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`suggestionId`,`userId`),
  KEY `fk_Votes_Days1_idx` (`suggestionId`),
  KEY `fk_Votes_Users1_idx` (`userId`),
  CONSTRAINT `fk_Votes_Days1` FOREIGN KEY (`suggestionId`) REFERENCES `suggestions` (`suggestionId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Votes_Users1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votes`
--

LOCK TABLES `votes` WRITE;
/*!40000 ALTER TABLE `votes` DISABLE KEYS */;
INSERT INTO `votes` VALUES (111,38),(113,38),(114,39),(116,39),(117,39),(118,39),(119,39),(120,13);
/*!40000 ALTER TABLE `votes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'reecepty'
--

--
-- Dumping routines for database 'reecepty'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-30 15:59:04
