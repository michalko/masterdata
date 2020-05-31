ALTER TABLE `brain1`.`topic` 
ADD COLUMN `parent_topic_id` INT NULL AFTER `current_posts`;
